package webapi.services;

import application.repositories.IUserRepository;
import domain.entities.user.Customer;
import domain.entities.user.Role;
import domain.entities.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webapi.configuration.JwtConfig;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtConfig jwtConfig;
    private final IUserRepository userRepository;

    private UserSession userSession;

    public User getUser() {
        if (userSession == null) return null;
        Optional<User> optionalUser = userRepository.findByUsername(userSession.username);
        return optionalUser.orElse(null);
    }

    public void setUserSession(User user) {
        this.userSession = new UserSession(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }
    public void setUserSession(String token) {
        this.userSession = getUserSessionFromToken(token);
    }

    public String generateToken(User user) {
        Date now = new Date();
        long expiryInMillis = TimeUnit.DAYS.toMillis(jwtConfig.getExpiration());
        Date expiryDate = new Date(now.getTime() + expiryInMillis);
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().toString())
                .issuedAt(now)
                .issuer(jwtConfig.getIssuer())
                .expiration(expiryDate)
                .signWith(getSignKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> jwt = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
            Claims payload = jwt.getPayload();
            Date now = new Date();
            return payload.getExpiration().after(now) &&
                   payload.getIssuedAt().before(now) &&
                   payload.getIssuer().equals(jwtConfig.getIssuer()) &&
                   payload.getSubject() != null &&
                   payload.get("role", String.class) != null &&
                   payload.get("username", String.class) != null &&
                   payload.get("email", String.class) != null;
        }
        catch (Exception e) {
            return false;
        }
    }

    private UserSession getUserSessionFromToken(String token) {
        Jws<Claims> jwt = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
        Claims payload = jwt.getPayload();
        Long id = Long.valueOf(payload.getSubject());
        String username = payload.get("username", String.class);
        String email = payload.get("email", String.class);
        Role role = Role.valueOf(payload.get("role", String.class));
        return new UserSession(id, username, email, role);
    }

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }

	public User findOrCreateGoogleUser(String name, String email) {
		Optional<User> userOpt = userRepository.findByEmail(email);
		if (userOpt.isPresent()) {
			return userOpt.get();
		}
		Customer customer = Customer
			.builder()
			.username(name)
			.password(null)
			.salt(null)
			.email(email)
			.address(null)
			.role(domain.entities.user.Role.CUSTOMER)
			.credit(0L)
			.build();
		return userRepository.save(customer);
	}

    public static record UserSession(Long id, String username, String email, Role role) {} // FIXME: dunno if word session is a good choice or not
}
