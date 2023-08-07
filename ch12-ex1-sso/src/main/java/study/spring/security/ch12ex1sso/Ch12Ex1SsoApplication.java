package study.spring.security.ch12ex1sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Ch12Ex1SsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ch12Ex1SsoApplication.class, args);
	}

}
