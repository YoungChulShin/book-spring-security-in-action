package study.spring.security.ch10ex1csrf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class WebConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(c -> c.anyRequest().authenticated())
        .formLogin(c -> c.defaultSuccessUrl("/main", true))
        .csrf(c -> {
          // 특정 패턴의 요청을 csrf에서 제외하는 방법을 정의한다.
          // var handlerMappingIntrospector = new HandlerMappingIntrospector();
          // var mvcRequestMatcher = new MvcRequestMatcher(handlerMappingIntrospector, "/hello");
          // c.ignoringRequestMatchers(mvcRequestMatcher);
        })
        .build();
  }
}
