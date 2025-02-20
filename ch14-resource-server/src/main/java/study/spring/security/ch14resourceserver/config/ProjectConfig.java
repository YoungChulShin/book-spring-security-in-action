package study.spring.security.ch14resourceserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

  private final String keySetUri;

  public ProjectConfig(
      @Value("${application.security.key-set-uri}") String keySetUri) {
    this.keySetUri = keySetUri;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(c -> {
          c.anyRequest().authenticated();
        })
        .oauth2ResourceServer(c -> {
          c.jwt(j -> {
            j.jwkSetUri(keySetUri);
          });
        });

    return http.build();
  }
}
