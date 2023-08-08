package study.spring.security.ch12ex1sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

  private final OAuth2ClientInfo oAuth2ClientInfo;

  public WebSecurityConfig(OAuth2ClientInfo oAuth2ClientInfo) {
    this.oAuth2ClientInfo = oAuth2ClientInfo;
  }

  // 1. 빈으로 직접 등록하는 방법도 있고,
  // 2. oauth2Login에 등록하는 방법도 있다.
  public ClientRegistrationRepository clientRegistrationRepository() {
    ClientRegistration clientRegistration = clientRegistration();
    return new InMemoryClientRegistrationRepository(clientRegistration);
  }

  private ClientRegistration clientRegistration() {
    return CommonOAuth2Provider.GITHUB
        .getBuilder("github")
        .clientId(oAuth2ClientInfo.clientId())
        .clientSecret(oAuth2ClientInfo.clientSecret())
        .build();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .oauth2Login(c -> {
          c.clientRegistrationRepository(clientRegistrationRepository());
        })
        .authorizeHttpRequests(c -> c.anyRequest().authenticated())
        .build();
  }
}
