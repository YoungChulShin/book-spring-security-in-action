# 설명
커스텀 `AuthenticationProvider`를 구현해봅니다.

# 사용
## Custom AuthenticationProvider 구현
CustomAuthenticationProvider 클래스
1. UserDetailsService를 주입받는다
   - UserDetailsService를 이용해서 사용자 정보를 식별한다
2. PasswordEncoder를 주입받는다
   - PasswordEncoder를 이용해서 비밀번호를 검사한다
3. `authenticate()` 메서드를 구현한다
   - 인증이 통과하면 `UsernamePasswordAuthenticationToken`를 리턴한다
   - 인증이 실패하면 `AuthenticationException`을 throw 한다
4. `CustomAuthenticationProvider`를 SecurityFilterChain에 등록한다

SecurityFilterChain에 Custom AuthenticationProvider 등록
- 'WebSecurityConfigureAdapter'가 Deprecated되었기 때문에, `SecurityFilterChain`을 반환하는 빈을 등록한다
   ```java
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider);

        return http.build();
    }
   ```
- 참고 자료: https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter