package study.spring.security.ch5ex1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

  private final AuthenticationProvider authenticationProvider;

  public ProjectConfig(AuthenticationProvider authenticationProvider) {
    this.authenticationProvider = authenticationProvider;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .httpBasic(Customizer.withDefaults())
        .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
        .authenticationProvider(authenticationProvider);

    return http.build();
  }

}
