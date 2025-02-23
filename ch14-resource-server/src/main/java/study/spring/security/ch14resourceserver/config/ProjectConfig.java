package study.spring.security.ch14resourceserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

  private final String keySetUri;
  private final String introspectionUri;
  private final String clientId;
  private final String clientSecret;
  private final JwtAuthenticationConverter converter;

  public ProjectConfig(
      @Value("${application.security.key-set-uri}") String keySetUri,
      @Value("${application.security.introspection-uri}") String introspectionUri,
      @Value("${application.security.resource-server.client-id}") String clientId,
      @Value("${application.security.resource-server.client-secret}") String clientSecret,
      JwtAuthenticationConverter converter) {
    this.keySetUri = keySetUri;
    this.introspectionUri = introspectionUri;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.converter = converter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(c -> {
          c.anyRequest().authenticated();
        })
        .oauth2ResourceServer(c -> {
          // 2개 중에 1개만 선택 가능하다.
          // jwt -> non opaque 토큰 사용
          // opaqueToken -> opaqueToken 사용
//          c.jwt(j -> {
//            j.jwkSetUri(keySetUri);
//            j.jwtAuthenticationConverter(converter);
//          });
          c.opaqueToken(o -> {
            o
                .introspectionUri(introspectionUri)
                .introspectionClientCredentials(clientId, clientSecret);
          });
        });

    return http.build();
  }
}
