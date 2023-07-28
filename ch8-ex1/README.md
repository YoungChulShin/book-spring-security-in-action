# 설명
특정 그룹에 제한을 적용하는 방법을 알아본다.

# 사용
## 1. URL 패턴을 이용한 권한 설정
샘플 코드
```kotlin
.authorizeHttpRequests {
    it.requestMatchers("/hello").hasRole("ADMIN")
    it.requestMatchers("/ciao").hasRole("MANAER")
    it.anyRequest().authenticated()
}
```

## 2. HTTP method와 URL 패턴을 이용한 권한 설정
샘플 코드
```kotlin
.authorizeHttpRequests {
    // GET a: 인증된 사용자만 허용
    it.requestMatchers(HttpMethod.GET, "/a").authenticated()
    // POST a: 모든 사용자 허용
    it.requestMatchers(HttpMethod.POST, "/a").permitAll()
    // GET a/b/**: a/b로 시작하는 모든 URL에 대해서 인증된 사용자는 허용
    it.requestMatchers(HttpMethod.GET, "/a/b/**").authenticated()
    it.anyRequest().denyAll()
}
```