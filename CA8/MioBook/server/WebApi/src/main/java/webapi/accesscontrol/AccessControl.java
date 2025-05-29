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
        Access access = getAccessAnnotation(joinPoint);
        List<Role> roles = Arrays.stream(access.roles()).toList();
        if (roles.isEmpty()) // If no roles are specified, we don't even check jwt so user be more comfortable, it's handled in frontend
            return;

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authHeader = request.getHeader("Authorization");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer "))
            token = authHeader.substring(7); // The part after "Bearer "
        if (token == null || !authenticationService.validateToken(token))
            throw AuthenticationException.noOneLoggedIn();

        authenticationService.setUserSession(token);
        User loggedInUser = authenticationService.getUser();

        if (loggedInUser == null)
            throw AuthenticationException.noOneLoggedIn();

        boolean isWhiteList = access.isWhiteList();
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
