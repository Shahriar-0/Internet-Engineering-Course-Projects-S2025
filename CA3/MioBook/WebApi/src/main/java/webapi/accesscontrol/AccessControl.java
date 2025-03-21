package webapi.accesscontrol;

import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
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
        List<User.Role> roles = Arrays.stream(access.roles()).toList();
        boolean isWhiteList = access.isWhiteList();

        if (!isWhiteList && roles.isEmpty())
            return;

        authenticationService.validateSomeOneLoggedIn();
        User logedInUser = authenticationService.getUser();

        boolean isRoleAppear = roles.contains(logedInUser.getRole());
        if ((isRoleAppear && !isWhiteList) || (!isRoleAppear && isWhiteList))
            throw new InvalidAccess(roles, logedInUser.getRole(), isWhiteList);
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
