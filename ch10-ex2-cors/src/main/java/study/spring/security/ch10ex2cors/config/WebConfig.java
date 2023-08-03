package study.spring.security.ch10ex2cors.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class WebConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(c -> c.disable())
//        .cors(c -> {
//          CorsConfigurationSource source = request -> {
//            CorsConfiguration config = new CorsConfiguration();
//            config.setAllowedOrigins(
//                List.of("http://localhost:8080")
//            );
//            config.setAllowedMethods(
//                List.of("POST")
//            );
//            return config;
//          };
//
//          c.configurationSource(source);
//        })
        .authorizeHttpRequests(c -> c.anyRequest().permitAll())
        .build();
  }
}
