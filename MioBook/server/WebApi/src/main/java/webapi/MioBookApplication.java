package webapi;

import  org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"infra.daos"})
@EnableJpaRepositories(basePackages = "infra.repositories.jpa")
@ComponentScan(basePackages = {
    "webapi",
    "application",
    "domain",
    "infra"
})
public class MioBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(MioBookApplication.class, args);
	}
}
