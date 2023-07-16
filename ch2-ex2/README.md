# 사용
## SecurityFilterChain
`WebSecurityConfigurerAdapter` 가 deprecated 되면서, `SecurityFilterChain`을 사용한다.
- https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

모든 요청 허용
```
authorizeHttpRequests((authz) -> authz.anyRequest().permitAll());
```