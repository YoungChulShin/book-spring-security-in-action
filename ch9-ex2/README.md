# 설명
특정 필터를 대체하는 방법을 알아봅니다. 

# 사용
`addFilterAt` 메서드를 이용해서 원하는 필터의 위치에 추가하고 싶은 필터를 추가할 수 있다. 

샘플 코드
```kotlin
@Bean
fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
        // staticKeyAuthenticationFilter 필터를 BasicAuthenticationFilter 위치에 추가한다.
        .addFilterAt(
            staticKeyAuthenticationFilter,
            BasicAuthenticationFilter::class.java)
        .authorizeHttpRequests {
            it.anyRequest().permitAll()
        }

    return http.build()
}
```

__중요한 점__
- 특정 위치에 필터를 추가한다고해서 해당 필터가 사라지는것은 아니다. 
   - 위의 예시에서 StaticKeyAuthenticationFilter를 추가한다고해서 BasicAuthenticationFilter가 대체되는것은 아니다. 
- 위 예시에서는 BasicAuthenticationFilter가를 비활성화 하기 위해서 `httpBasic` 인증을 추가하지 않았다. 
