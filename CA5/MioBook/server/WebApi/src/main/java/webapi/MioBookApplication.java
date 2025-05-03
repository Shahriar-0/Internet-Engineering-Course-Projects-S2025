package webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"infra.daos"})
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
