package cli.configuration;

import application.services.AlakiService;
import application.services.Falaki;
import application.services.Moftaki;
import infra.services.AlakiServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfiguration {
    AlakiService alakiService = new AlakiServiceImpl();
    Falaki falaki = new Falaki(alakiService);
    Moftaki moftaki = new Moftaki();
    @Bean
    public AlakiService alakiService() {
        return alakiService;
    }

    @Bean
    public Falaki falaki() {
        return falaki;
    }

    @Bean
    public Moftaki moftaki() {
        return moftaki;
    }
}
