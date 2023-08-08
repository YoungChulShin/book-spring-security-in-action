# 설명
github을 권한 부여 서버와 리소스 서버로 이용하는 클라이언트 프로그램을 구현합니다. 

# 사용
## OAuth2 클라이언트 활성화
`spring-boot-starter-oauth2-client` 의존성을 추가한다. 
```groovy
implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
```

## OAuth2 github 리소스 서버 설정
github 사이트에서 OAuth2 Application을 등록한다. 
- 경로: settings -> developer settings -> OAuth Apps 
- application을 등록하고, `client-id`, `client-secret`을 발급받는다. 

## OAuth2 인증 사용
### SecurityFilterChain 설정
인증 방법에 `oauth2Login`을 추가한다. 
```java
@Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .oauth2Login(c -> { // 생략
```

### oauth2Login 설정에 대한 필터 활성화
oauth2Login을 선택하면 `OAuth2LoginAuthenticationFilter`가 활성화된다. 

OAuth2LoginAuthenticationFilter필터는 내부적으로 `ClientRegistrationRepository`의 구현체를 필요로한다. 

ClientRegistrationRepository 인터페이스
- 코드
   ```java
   ClientRegistration findByRegistrationId(String registrationId);
   ```
- `UserDetailsService`와 비슷한 동작을 한다. registrationId를 받아서 `ClientRegistration`을 응답한다. 
- ClientRegistration는 OAuth 연결을 위한 정보를 가지고 있는 클래스이다. 구글/페이스북 등은 스프링 시큐리티에서 구성할 수 있는 코드를 제공해준다. 
   ```java
    // GITHUB 샘플
    private ClientRegistration clientRegistration() {
        return CommonOAuth2Provider.GITHUB
            .getBuilder("github")
            .clientId(oAuth2ClientInfo.clientId())
            .clientSecret(oAuth2ClientInfo.clientSecret())
            .build();
   ```
- 등록 방법
   1. @Bean을 이용해서 등록하는 방법
      ```java
      public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration = clientRegistration();
        return new InMemoryClientRegistrationRepository(clientRegistration);
      }
      ```
   2. FilterChain을 이용하는 방법
      ```java
      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         return http
            .oauth2Login(c -> {c.clientRegistrationRepository(clientRegistrationRepository());})
      ```
    3. 설정 정보를 이용하는 방법
       ```properties
       spring.security.oauth2.client.registration.github.client-id= xxx
       spring.security.oauth2.client.registration.github.client-secret= xxx
       ```

## 최종 테스트 시나리오
1. 서버 실행
2. `http://localhost:8080` 접속
3. 구글 로그인 페이지 출력
4. 로그인 완료되면 페이지 접속 