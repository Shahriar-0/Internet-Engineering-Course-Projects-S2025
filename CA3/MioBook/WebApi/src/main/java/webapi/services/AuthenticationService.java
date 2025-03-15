package webapi.services;

import domain.entities.User;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private User user;

    public User.Role getUserRole() {
        return user.getRole();
    }
    public String getUserName() {
        return user.getUsername();
    }
    
    public void setLoggedInUser(User user) {
        this.user = user;
    }
}
