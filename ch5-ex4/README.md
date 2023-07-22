# 설명
1. `양식 기반 인증`을 이용한 로그인 방법을 알아봅니다.

# 사용
## 양식 기반 인증 사용
### 적용 방법
`SecurityFilterChain`을 설정할 때, 인증 방법에 `formLogin`을 추가하면 적용됩니다. 
```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .formLogin(c -> {
            c.defaultSuccessUrl("/home", true);
        })
        .authorizeHttpRequests(c -> c.anyRequest().authenticated());

    return http.build();
}
```

### 인증 결과에 대한 처리
`AuthenticationSuccessHandler` 인터페이스 구현
- 인증이 성공했을 때 추가적인 핸들러 처리를 해줄 수 있다

`AuthenticationFailureHandler` 인터페이스 구현
- 인증이 실패했을 때 추가적인 핸들러 처리를 해줄 수 있다

생성한 핸들러는 등록 이후에 사용할 수 있다
```java
http
    .formLogin(c -> {
        c.defaultSuccessUrl("/home", true);
        c.successHandler(new CustomAuthenticationSuccessHandler());
        c.failureHandler(new CustomAuthenticationFailureHandler());
    })
```

### Basic 인증과 함께 사용
`formLogin`만 사용하면, Http Basic 방식으로 접근하려고 할 때 `'302 - Redirect'` 응답을 전달받게 된다.

이때에는 인증 방식에 'httpBasic'을 추가해주면 된다. 
```java
http
    .formLogin(c -> {
        c.defaultSuccessUrl("/home", true);
        c.successHandler(new CustomAuthenticationSuccessHandler());
        c.failureHandler(new CustomAuthenticationFailureHandler());
    })
    .httpBasic(c -> { })
    .authorizeHttpRequests(c -> c.anyRequest().authenticated());
```