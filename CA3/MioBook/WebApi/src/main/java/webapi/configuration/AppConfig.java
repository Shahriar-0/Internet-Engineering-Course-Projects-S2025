package webapi.configuration;

import application.services.AdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    private final AppContext context = new AppContext();

    @Bean
    public AdminService adminService() {
        return context.getAdminService();
    }
}
