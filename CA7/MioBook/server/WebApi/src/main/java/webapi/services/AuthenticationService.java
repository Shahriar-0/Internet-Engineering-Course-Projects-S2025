package webapi.services;

import application.repositories.IUserRepository;
import domain.entities.user.Admin;
import domain.entities.user.Customer;
import domain.entities.user.Role;
import domain.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final int SESSION_TIME_TO_LIVE_MINUTE = 20;
    public static final String SESSION_KEY_STR = "Session-Id";
    private static final String SESSION_PATH_PATTERN = "sessions:%s";

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
