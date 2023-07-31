# 설명
스프링 스큐리티에서 필터를 추가하는 방법을 알아본다. 

# 사용
## 필터 추가
`jakarta.servlet.Filter` 인터페이스를 구현한다. 인터페이스는 `doFilter` 메서드를 가진다. 

```java
void doFilter(
    ServletRequest request, 
    ServletResponse response, 
    FilterChain chain) throws IOException, ServletException;
```

응답 설정
- 조건을 만족하지 못한다면 `ServletResponse`에 응답 값을 설정하고, return 한다. 
- 조건을 만족했다면 `FilterChain`을 통해서 doFilter를 호출하면 다음에 설정된 필터가 호출된다.

`RequestValidationFilter`,  `AuthenticationLoggingFilter` 코드를 참고한다. 
```kotlin
// RequestValidationFilter
class RequestValidationFilter: Filter {

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        val requestId = httpRequest.getHeader("Request-Id")

        // 조건을 만족하지 않으면 응답 설정 및 리턴
        if (requestId == null || requestId.isBlank()) {
            httpResponse.status = HttpServletResponse.SC_BAD_REQUEST
            return
        }

        // 조건을 만족하면 doFilter를 호출
        chain.doFilter(request, response)
    }
}
```

## 필터 위치 설정
`SecurityFilterChain` 에서 필터 위치를 선정할 수 있다. 

### 특정 필터의 앞/뒤에 필터 설정
아래 샘플 코드를 참고한다. 
```kotlin
@Bean
fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
        // BasicAuthenticationFilter 앞에 RequestValidationFilter를 설정
        .addFilterBefore(
            RequestValidationFilter(),
            BasicAuthenticationFilter::class.java)
        // BasicAuthenticationFilter 뒤에 AuthenticationLoggingFilter를 설정
        .addFilterAfter(
            AuthenticationLoggingFilter(),
            BasicAuthenticationFilter::class.java)
        .authorizeHttpRequests {
            it.anyRequest().permitAll()
        }

    return http.build()
}
```