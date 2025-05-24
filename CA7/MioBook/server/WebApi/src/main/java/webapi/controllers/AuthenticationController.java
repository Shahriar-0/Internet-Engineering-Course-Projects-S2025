package webapi.controllers;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpMethod.GET;

import application.exceptions.BaseException;
import application.exceptions.businessexceptions.userexceptions.UserNotFound;
import application.exceptions.businessexceptions.userexceptions.WrongPassword;
import application.result.Result;
import application.usecase.UseCaseType;
import application.usecase.user.account.Login;
import domain.entities.user.User;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import webapi.accesscontrol.Access;
import webapi.response.Response;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private static final String LOGIN_MESSAGE = "User logged in successfully";
	private static final String LOGOUT_MESSAGE = "User logged out successfully";
	private static final String WRONG_USERNAME_OR_PASSWORD_MESSAGE = "Username/Email or Password is not correct";

	private final UseCaseService useCaseService;
	private final AuthenticationService authenticationService;

	@Value("${google.client.id}")
	private String googleClientId;

	@Value("${google.client.secret}")
	private String googleClientSecret;

	@Value("${google.redirect.uri}")
	private String googleRedirectUri;

	@PostMapping("login")
	@Access(isWhiteList = true)
	public Response<?> login(@Valid @RequestBody Login.LoginData data) {
		Login useCase = (Login) useCaseService.getUseCase(UseCaseType.LOGIN);

		Result<User> userResult = useCase.perform(data);
		if (userResult.isFailure()) return processFailureOfLogin(userResult.exception());

		authenticationService.setUserSession(userResult.data());
		String jwt = authenticationService.generateToken(userResult.data());
		return Response.of(userResult.data().getRole().getValue(), OK, LOGIN_MESSAGE, jwt);
	}

	@DeleteMapping("logout")
	@Access(isWhiteList = false)
	public Response<?> logout() {
		return Response.of(OK, LOGOUT_MESSAGE);
	}

	@GetMapping("/google/login")
	@Access(isWhiteList = true)
	public Response<?> googleLogin() {
		String googleAuthUrl = UriComponentsBuilder
			.fromUriString("https://accounts.google.com/o/oauth2/auth")
			.queryParam("client_id", googleClientId)
			.queryParam("redirect_uri", googleRedirectUri)
			.queryParam("response_type", "code")
			.queryParam("scope", "openid email profile")
			.build()
			.toUriString();
		return Response.of(OK, googleAuthUrl);
	}

	@GetMapping("/google/callback")
	@Access(isWhiteList = true)
	public Response<?> googleCallback(@RequestParam("code") String code) {
		RestTemplate restTemplate = new RestTemplate();
		String tokenUrl = "https://oauth2.googleapis.com/token";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", googleClientId);
		params.add("client_secret", googleClientSecret);
		params.add("code", code);
		params.add("grant_type", "authorization_code");
		params.add("redirect_uri", googleRedirectUri);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		var tokenResponse = restTemplate.postForObject(tokenUrl, request, Map.class);
		String accessToken = tokenResponse != null ? (String) tokenResponse.get("access_token") : null;
		if (accessToken == null)
			return Response.of(UNAUTHORIZED, "Google authentication failed");

		HttpHeaders userHeaders = new HttpHeaders();
		userHeaders.set("Authorization", "Bearer " + accessToken);
		HttpEntity<String> userRequest = new HttpEntity<>(userHeaders);
		ResponseEntity<Map<String, Object>> userInfoResponse = restTemplate.exchange(
			"https://www.googleapis.com/oauth2/v2/userinfo",
			GET,
			userRequest,
			new ParameterizedTypeReference<Map<String, Object>>() {}
		);
		Map<String, Object> userInfo = userInfoResponse.getBody();
		if (userInfo == null || userInfo.get("email") == null) {
			return Response.of(UNAUTHORIZED, "Google user info fetch failed");
		}

		String email = (String) userInfo.get("email");
		String name = (String) userInfo.getOrDefault("name", email);

		User user = authenticationService.findOrCreateGoogleUser(name, email);
		authenticationService.setUserSession(user);
		String jwt = authenticationService.generateToken(user);
		return Response.of(user.getRole().getValue(), OK, LOGIN_MESSAGE, jwt);
	}

	private static Response<?> processFailureOfLogin(BaseException exception) {
		if (!(exception instanceof UserNotFound || exception instanceof WrongPassword))
			throw exception;

		return Response.of(UNAUTHORIZED, WRONG_USERNAME_OR_PASSWORD_MESSAGE);
	}
}
