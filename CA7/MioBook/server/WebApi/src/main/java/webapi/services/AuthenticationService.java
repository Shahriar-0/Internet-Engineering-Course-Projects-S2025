package webapi.services;

import application.repositories.IUserRepository;
import webapi.configuration.JwtConfig;
import domain.entities.user.Role;
import domain.entities.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final int SESSION_TIME_TO_LIVE_MINUTE = 20;
    public static final String SESSION_KEY_STR = "Session-Id";
    private static final String SESSION_PATH_PATTERN = "sessions:%s";

    private final JwtConfig jwtConfig;
    private final RedisTemplate<String, UserSession> sessionStorage;
    private final IUserRepository userRepository;

    private UserSession userSession;

    public User getUser() {
        Optional<User> optionalUser = userRepository.findByUsername(userSession.username);
        return optionalUser.orElse(null);
    }

    public String createSession(User user) {
        String sessionId = UUID.randomUUID().toString();
        UserSession session = new UserSession(user, sessionId);
        sessionStorage.opsForValue().set(getSessionPath(sessionId), session, Duration.ofMinutes(SESSION_TIME_TO_LIVE_MINUTE));
        return sessionId;
    }

    public User setUserBySessionId(String sessionId) {
        if (sessionId == null)
            return null;

        UserSession session = sessionStorage.opsForValue().get(getSessionPath(sessionId));
        if (session == null)
            return null;

        sessionStorage.expire(getSessionPath(sessionId), Duration.ofMinutes(SESSION_TIME_TO_LIVE_MINUTE));
        this.userSession = session;
        return getUser();
    }

	public boolean deleteSession() {
        if (userSession == null || userSession.sessionId == null)
            return false;

        return sessionStorage.delete(getSessionPath(userSession.sessionId));
    }

    private String getSessionPath(String sessionId) {
        return String.format(SESSION_PATH_PATTERN, sessionId);
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getExpiration());

        return Jwts.builder()
                .subject(user.getId().toString()) // FIXME: not sure
                .claim("username", user.getId())
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
            return payload.getExpiration().after(new Date()) &&
                   payload.getIssuedAt().before(new Date()) &&
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

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSession {
        Long id;
        String username;
        Role role;
        String sessionId;

        UserSession(User user, String sessionId) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.role = user.getRole();
            this.sessionId = sessionId;
        }
    }
}
