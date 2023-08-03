# 설명
CORS가 발생하는 상황을 확인해보고, 대응 방법을 코딩해본다. 

# 사용
## 테스트 케이스 설명
테스트 시나리오
1. `http://localhost:8080/` endpoint를 호출한다.
2. api는 `main.html` 파일을 응답한다. 
3. main.html 파일은 내부적으로 `http://127.0.0.1:8080/test`를 호출하는 스크립트가 들어가있다. 

CORS 설정이 안되어있다면?
- `localhost:8080`으로 로그인해서 `http://127.0.0.1:8080`을 호출하는 것이기 때문에 브라우저에서 CORS 에러가 발생한다. 
   ```
   Access to XMLHttpRequest at 'http://127.0.0.1:8080/test' from origin 'http://localhost:8080' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resourc
   ```

## CORS 에러 처리 v1
`@CrossOrigin` 애노테이션을 이용하는 방법. 
- `MainController#test()`에 관련 코드가 정의되어 있다. 
- 개별 메서드별로 설정할 수 있다는 장점이 있지만, 반복 코드가 많아지는 단점도 있다. 

코드 
```java
// test 메서드가 호출될 때, http://localhost:8080에서 접속하는 것이라면 Cross Origin을 허용한다.
@CrossOrigin(value = "http://localhost:8080")
public String test() { ... }
```

## CORS 에러 처리 v2
`SecurityFilterChain`에 CORS 관련 정보를 등록하는 방법. 
- `WebConfig#securityFilterChain#cors`에 관련 코드가 정의되어 있다. 

`CorsConfigurationSource`에 `AllowedOrigins`를 추가해주면 설정 가능하다. 
```java
.cors(c -> {
    // CorsConfigurationSource 정의
    CorsConfigurationSource source = request -> {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:8080"));
    config.setAllowedMethods(List.of("POST"));
    return config;
    };

    c.configurationSource(source);
})
```

## 응답 헤더 변경
위와 같이 코드를 변경하고 테스트하면 `test()`를 호출할 때 응답헤더에 아래 값이 추가된다. 
```
key: Access-Control-Allow-Origin
value: http://localhost:8080
```