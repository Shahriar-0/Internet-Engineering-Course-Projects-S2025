package cli.configuration;

import application.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfiguration {
    private final AppContext context = new AppContext();
    @Bean
    public UserService userService() {
        return context.getUserService();
    }
}
