package study.spring.ch6ex12.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebConfig {

  private final AuthenticationProvider authenticationProvider;

  public WebConfig(AuthenticationProvider authenticationProvider) {
    this.authenticationProvider = authenticationProvider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .formLogin(c -> c.defaultSuccessUrl("/main", true))
        .authorizeHttpRequests(c -> c.anyRequest().authenticated())
        .authenticationProvider(authenticationProvider);

    return http.build();
  }

}
