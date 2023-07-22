package study.spring.ch5ex4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .formLogin(c -> {
          c.defaultSuccessUrl("/home", true);
          c.successHandler(new CustomAuthenticationSuccessHandler());
          c.failureHandler(new CustomAuthenticationFailureHandler());
        })
        .httpBasic(c -> { })
        .authorizeHttpRequests(c -> c.anyRequest().authenticated());

    return http.build();
  }

}
