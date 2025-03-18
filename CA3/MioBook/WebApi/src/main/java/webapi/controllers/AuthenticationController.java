package webapi.controllers;

import application.usecase.user.LoginUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginUseCase.LoginData data, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(data.username(), data.password());

		Authentication authentication = authenticationManager.authenticate(authRequest);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		HttpSession session = request.getSession(true);
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

		return ResponseEntity.ok("User logged in successfully");
	}

	@DeleteMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		SecurityContextHolder.clearContext();
		return ResponseEntity.ok("User logged out successfully");
	}
}
