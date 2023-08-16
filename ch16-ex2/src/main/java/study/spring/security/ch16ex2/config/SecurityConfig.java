package study.spring.security.ch16ex2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

  private final DocumentPermissionEvaluator documentPermissionEvaluator;

  public SecurityConfig(DocumentPermissionEvaluator documentPermissionEvaluator) {
    this.documentPermissionEvaluator = documentPermissionEvaluator;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    var service = new InMemoryUserDetailsManager();

    var u1 = User.withUsername("ycshin")
        .password("12345")
        .roles("admin")
        .build();

    var u2 = User.withUsername("mjseo")
        .password("12345")
        .roles("manager")
        .build();

    service.createUser(u1);
    service.createUser(u2);

    return service;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
    var expressionHandler = new DefaultMethodSecurityExpressionHandler();
    expressionHandler.setPermissionEvaluator(documentPermissionEvaluator);

    return expressionHandler;
  }

}
