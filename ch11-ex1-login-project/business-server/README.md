# 설명
사용자의 요청을 받아서 2단계 인증을 진행하는 비지니스 로직 서버를 구현합니다. 

# 사용
## 인증 기능
`/Login`을 통해서 인증을 구현할 때, 2단계의 인증을 진행합니다. 
1. 'username', 'password'를 이용해서 otp code를 발급합니다. 
2. 'username', 'otp code'를 이용해서 jwt token을 발급합니다. 
3. 'jwt token'을 이용해서 요청을 처리합니다. 

`InitialAuthenticationFilter` 클래스에 1, 2 단계의 기능이 구현되어있습니다. 
- `shouldNotFilter` 메서드를 이용해서 'login' 요청에 대해서만 필터가 동작합니다. 
   ```java
   @Override
   protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
      return !request.getServletPath().equals("/login");
   }
   ```
- header에서 필요한 필드를 전달 받고, 필드의 값 유무를 기준으로 '비밀번호 검증', 'OTP 검증' 요청을 구분합니다. 
   - 비밀번호 검증: `UsernamePasswordAuthentication` 을 이용합니다. 
   - OTP 검증: `OtpAuthentication`을 이용합니다. 

비밀번호와 OTP Code 검증을 지원하기 위해서 2개의 `AuthenticationProvider` 구현체를 등록합니다. 
- 구현체 정보
   1. `UsernamePasswordAuthenticationProvider` 
   2. `OtpAuthenticationProvider`
- AuthenticationManager와 AuthenticationProvider의 연결
   - AuthenticationProvider의 `supports` 메서드를 통해서 적용 여부를 설정할 수 있습니다. 
   - 2개의 검증을 위해서 각각의 Authentcation 클래스를 만들었는데, 각 Provider에서 이 부분을 활용할 수 있습니다. 
      ```java
      // UsernamePasswordAuthenticationProvider의 supports 메서드
      @Override
      public boolean supports(Class<?> authentication) {
        // authentication이 UsernamePasswordAuthentication일 경우에만 동작하도록 설정합니다. 
        return UsernamePasswordAuthentication.class.isAssignableFrom(authentication);
      }
      ```

### OTP Code 발급
'username', 'password' 인증이 되면 OTP Code가 발급됩니다. OTP Code는 Auth 서버에서 사용자에게 sms를 통해서 전달됩니다. 

### JWT Token 발급
'username', 'otp code' 인증이 되면 JWT Token을 생성하고, 응답 Header의 `Authorization`에 이 값을 포함해서 전달합니다.

```java
SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
String jwt = Jwts.builder()
    .setClaims(Map.of("username", username))
    .signWith(key)
    .compact();
response.setHeader("Authorization", jwt);
```

## JWT Token 검증
Login외의 요청에 대해서는 JWT Token이 올바른지 검증되어야합니다. 이를 위해서 `JwtAuthenticationFilter`를 추가합니다. 

Login 외의 요청에 대해서 처리하는 부분은 `shouldNotFilter` 메서드를 이용합니다. 
```java
@Override
protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return request.getServletPath().equals("/login");
}
```

JwtAuthenticationFilter 역할
1. JWT 토큰이 올바른지 검사합니다. 
2. Authentication을 설정합니다. 
   ```java
   GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("user");
   var auth = new UsernamePasswordAuthentication(
        username,
        null,
        List.of(grantedAuthority));
   SecurityContextHolder.getContext().setAuthentication(auth);
   ```
   - `UsernamePasswordAuthentication` 구현체가 생성될 때, 3개의 파라미터를 받는 구현체는 `authenticated`를 true로 설정해서 인증이 완료된것으로 처리됩니다. 

