package study.spring.security.businessserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import study.spring.security.businessserver.authentication.filter.InitialAuthenticationFilter;
import study.spring.security.businessserver.authentication.filter.JwtAuthenticationFilter;
import study.spring.security.businessserver.authentication.provider.OtpAuthenticationProvider;
import study.spring.security.businessserver.authentication.provider.UsernamePasswordAuthenticationProvider;

@Configuration
public class WebSecurityConfig {

  private final InitialAuthenticationFilter initialAuthenticationFilter;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final OtpAuthenticationProvider otpAuthenticationProvider;
  private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

  public WebSecurityConfig(
      InitialAuthenticationFilter initialAuthenticationFilter,
      JwtAuthenticationFilter jwtAuthenticationFilter,
      OtpAuthenticationProvider otpAuthenticationProvider,
      UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider) {
    this.initialAuthenticationFilter = initialAuthenticationFilter;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.otpAuthenticationProvider = otpAuthenticationProvider;
    this.usernamePasswordAuthenticationProvider = usernamePasswordAuthenticationProvider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .addFilterBefore(
            initialAuthenticationFilter,
            BasicAuthenticationFilter.class)
        .addFilterAfter(
            jwtAuthenticationFilter,
            BasicAuthenticationFilter.class)
        .authenticationProvider(otpAuthenticationProvider)
        .authenticationProvider(usernamePasswordAuthenticationProvider)
        .authorizeHttpRequests(c -> c.anyRequest().authenticated())
        .build();
  }
}
