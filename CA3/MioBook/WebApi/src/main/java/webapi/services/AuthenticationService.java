package webapi.services;

import domain.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import webapi.exceptions.AuthenticationException;


@Service
public class AuthenticationService {

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        throw AuthenticationException.noOneLoggedIn();
    }

    public User.Role getUserRole() {
        return getCurrentUser().getRole();
    }

    public String getUserName() {
        return getCurrentUser().getUsername();
    }
}