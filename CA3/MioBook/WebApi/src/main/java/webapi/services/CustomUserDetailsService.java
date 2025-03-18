package webapi.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import application.repositories.IUserRepository;
import application.result.Result;
import domain.entities.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result<User> userResult = userRepository.get(username);
        if (userResult.isFailure())
            throw new UsernameNotFoundException("User not found");
        User user = userResult.getData();
        return new CustomUserDetails(user);
    }
}