# 설명
1. 커스텀 `AuthenticationProvider`를 구현해봅니다.
2. 비동기 Thread로 SecurityContext가 전파되는 방법을 알아봅니다. 
3. 인증이 실패했을 때, 응답 데이터를 추가 구성하는 방법을 알아봅니다. 

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
4. `supports()` 메서드를 구현한다
   - 이 메서드를 통해서 여러 인증 방법에 대해서 대응될 수 있도록 할 수 있다
5. `CustomAuthenticationProvider`를 SecurityFilterChain에 등록한다

추가되는 `AuthenticationProvider`는 내부적으로 `AuthenticationManager`가 가지고 있는 `List<AuthenticationProvider>`에 등록된다. 


SecurityFilterChain에 Custom AuthenticationProvider 등록
- 'WebSecurityConfigureAdapter'가 Deprecated되었기 때문에, `SecurityFilterChain`을 반환하는 빈을 등록한다
   ```java
   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
        .httpBasic(Customizer.withDefaults())
        .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
        .authenticationProvider(authenticationProvider);

      return http.build();
   }
   ```
- 참고 자료: https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

