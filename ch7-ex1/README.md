# 설명
사용자 권한을 기반으로 엔드포인트에 대한 엑세스를 구성하는 방법을 알아본다

# 사용
## 특정 권한을 가지고 있을 경우 접근 제어
`hasAuthority` 사용
```java
return http
    .httpBasic(Customizer.withDefaults())
    .authorizeHttpRequests(c -> c.anyRequest().hasAuthority("WRITE"))
    .build();
```

## N개 권한 중 하나를 가지고 있을 경우 접근 제어
`hasAnyAuthority` 사용
```java
return http
    .httpBasic(Customizer.withDefaults())
    .authorizeHttpRequests(c -> c.anyRequest().hasAnyAuthority("WRITE", "READ"))
    .build();
```

## 복잡한 접근 제어
`access` 사용

