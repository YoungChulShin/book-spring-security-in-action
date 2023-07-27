package study.spring.security.ch7ex1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebConfig {

  //////////////////////////////////////////////
  // 권한 기반 접근 제어
  //////////////////////////////////////////////
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    // WRITE 권한만 허용
//    return http
//        .httpBasic(Customizer.withDefaults())
//        .authorizeHttpRequests(c -> c.anyRequest().hasAuthority("WRITE"))
//        .build();
//
//    // WRITE와 READ중 하나라도 가지고 있으면 허용
////    return http
////        .httpBasic(Customizer.withDefaults())
////        .authorizeHttpRequests(c -> c.anyRequest().hasAnyAuthority("WRITE", "READ"))
////        .build();
//
////    String expression = "hasAuthority('read') and !hasAuthority('delete')";
////    return http
////        .httpBasic(Customizer.withDefaults())
////        .authorizeHttpRequests(c -> c.anyRequest().access(expression))
////        .build();
//  }

  //////////////////////////////////////////////
  // 역할 기반 접근 제어
  //////////////////////////////////////////////
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // MANAGER 역할만 허용
    return http
        .httpBasic(Customizer.withDefaults())
        .authorizeHttpRequests(c -> c.anyRequest().hasRole("MANAGER"))
        .build();
  }
}
