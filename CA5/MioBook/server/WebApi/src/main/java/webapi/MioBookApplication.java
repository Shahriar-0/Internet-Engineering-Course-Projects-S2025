package webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"infra.daos"})
public class MioBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(MioBookApplication.class, args);
	}
}
