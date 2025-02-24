package study.spring.security.ch15oauth2client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .oauth2Login(Customizer.withDefaults()) // use oauth2 login
        .authorizeHttpRequests(
            c -> c.anyRequest().authenticated())
        .build();
  }

}
