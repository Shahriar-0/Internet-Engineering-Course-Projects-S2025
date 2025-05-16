package webapi.accesscontrol;

import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import domain.entities.user.Role;
import domain.entities.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import webapi.exceptions.AuthenticationException;
import webapi.services.AuthenticationService;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class AccessControl {
    private final AuthenticationService authenticationService;

    @Before("@annotation(webapi.accesscontrol.Access)")
    public void checkAccess(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String sessionId = request.getHeader(AuthenticationService.SESSION_KEY_STR);
        User loggedInUser = authenticationService.setUserBySessionId(sessionId);

        Access access = getAccessAnnotation(joinPoint);
        List<Role> roles = Arrays.stream(access.roles()).toList();
        boolean isWhiteList = access.isWhiteList();
        if (!isWhiteList && roles.isEmpty())
            return;

        if (loggedInUser == null)
            throw AuthenticationException.noOneLoggedIn();

        boolean isRoleAppear = roles.contains(loggedInUser.getRole());
        if ((isRoleAppear && !isWhiteList) || (!isRoleAppear && isWhiteList))
            throw new InvalidAccess(roles, loggedInUser.getRole(), isWhiteList);
    }

    private static Access getAccessAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Access access = method.getAnnotation(Access.class);
        if (access == null)
            throw new RuntimeException("Access annotation not found!");

        return access;
    }
}
