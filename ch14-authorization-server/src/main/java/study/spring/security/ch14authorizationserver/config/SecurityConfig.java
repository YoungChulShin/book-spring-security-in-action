package study.spring.security.ch14authorizationserver.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class SecurityConfig {

  @Bean
  @Order(1)
  public SecurityFilterChain asFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfiguration
        .applyDefaultSecurity(http);

    // enable OpenId Connect protocol
    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
        .oidc(Customizer.withDefaults());

    http.exceptionHandling((e) -> {
      e.authenticationEntryPoint(
          new LoginUrlAuthenticationEntryPoint("/login"));
    });

    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    return http
        .formLogin(Customizer.withDefaults()) // enable form-login
        .authorizeHttpRequests(c -> c.anyRequest().authenticated())
        .build();
  }

  // 사용자 정보를 관리
  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails userDetails = User.withUsername("ycshin")
        .password("password")
        .roles("USER")
        .build();

    return new InMemoryUserDetailsManager(userDetails);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  // 클라이언트 정보를 관리
  // RegisteredClient는 UserDetails와 비슷하고
  // RegisteredClientRepository는 UserDtailService와 비슷하다.
  @Bean
  public RegisteredClientRepository registeredClientRepository() {
    RegisteredClient registeredClient =
        RegisteredClient
            .withId(UUID.randomUUID().toString()) // client unique id (internal)
            .clientId("client") // external client identifier
            .clientSecret("secret") // client password
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantTypes(grantTypes -> {
              grantTypes.add(AuthorizationGrantType.AUTHORIZATION_CODE);
              grantTypes.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
              grantTypes.add(AuthorizationGrantType.REFRESH_TOKEN);
            })
//            .tokenSettings(
//                TokenSettings.builder()
//                    .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
//                    .accessTokenTimeToLive(Duration.ofMinutes(5L))
//                    .refreshTokenTimeToLive(Duration.ofHours(1L))
//                    .build()
//
//            )
            .redirectUri("https://www.manning.com/authorized")
            // Defines a purpose for the request of an access token.
            // The scope can be used later in authorization rules.
            .scope(OidcScopes.OPENID)
            .build();

    return new InMemoryRegisteredClientRepository(registeredClient);
  }

//  @Bean
//  public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
//    return context -> {
//      JwtClaimsSet.Builder claims = context.getClaims();
//      claims.claim("priority", "HIGH");
//    };
//  }

  @Bean
  public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
    return context -> {
      JwtClaimsSet.Builder claims = context.getClaims();
      claims.claim("priority", "HIGH");
    };
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

    keyPairGenerator.initialize(2048);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();

    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

    RSAKey rsaKey = new RSAKey.Builder(publicKey)
        .privateKey(privateKey)
        .keyID(UUID.randomUUID().toString())
        .build();

    JWKSet jwkSet = new JWKSet(rsaKey);
    return new ImmutableJWKSet<>(jwkSet);
  }

  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder().build();
  }
}
