package webapi.services;

import domain.entities.User;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    //TODO: Implement after adding logging mechanism
    public User.Role getUserRole() {
        throw new RuntimeException("Not implement yet!");
    }
}
