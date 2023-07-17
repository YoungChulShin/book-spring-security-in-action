# 설명
`SecurityFilterChain`을 이용해서 request 별로 인증을 적용하는 방법을 확인한다. 

# 사용
## SecurityFilterChain
`WebSecurityConfigurerAdapter` 가 deprecated 되면서, `SecurityFilterChain`을 사용한다.
- https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

모든 요청 허용
```
authorizeHttpRequests((authz) -> authz.anyRequest().permitAll());
```