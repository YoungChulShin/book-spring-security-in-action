# 설명
특정 그룹에 제한을 적용하는 방법을 알아본다.

# 사용
## Matchers를 이용한 권한 설정
샘플 코드
```kotlin
.authorizeHttpRequests {
    it.requestMatchers("/hello").hasRole("ADMIN")
    it.requestMatchers("/ciao").hasRole("MANAER")
    it.anyRequest().authenticated()
}
```