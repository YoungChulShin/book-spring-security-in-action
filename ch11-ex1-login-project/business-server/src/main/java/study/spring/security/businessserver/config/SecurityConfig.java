package study.spring.security.businessserver.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
public class SecurityConfig {

  // authenticationProvider가 1개 일 경우
//  @Bean
//  AuthenticationManager authenticationManager(
//      AuthenticationConfiguration authenticationConfiguration) throws Exception {
//    return authenticationConfiguration.getAuthenticationManager();
//  }

  // authenticationProvider가 2개 이상일 경우
  @Bean
  public AuthenticationManager authenticationManager(List<AuthenticationProvider> authenticationProviders) {
    return new ProviderManager(authenticationProviders);
  }
}
