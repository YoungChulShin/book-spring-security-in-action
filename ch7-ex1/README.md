# 설명
사용자 권한(Authority)을 기반으로 엔드포인트에 대한 엑세스를 구성하는 방법을 알아본다.

사용자의 역할(Role)을 기반으로 엔드포인트에 대한 엑세스를 구성하는 방법을 알아본다.

# 사용
## 역할 기반
### 특정 권한을 가지고 있을 경우 접근 제어
`hasAuthority` 사용
```java
return http
    .httpBasic(Customizer.withDefaults())
    .authorizeHttpRequests(c -> c.anyRequest().hasAuthority("WRITE"))
    .build();
```

### N개 권한 중 하나를 가지고 있을 경우 접근 제어
`hasAnyAuthority` 사용
```java
return http
    .httpBasic(Customizer.withDefaults())
    .authorizeHttpRequests(c -> c.anyRequest().hasAnyAuthority("WRITE", "READ"))
    .build();
```

### 복잡한 접근 제어
`access` 사용

## 권한 기반
### 권한 추가
샘플
```java
// roles 이용
User.withUsername("ycshin-m")
    .password("12345")
    .roles("MANAGER")
    .build();

// authorities 이용
User.withUsername("ycshin-m")
    .password("12345")
    .authorities("ROLE_MANAGER")
    .build();
```

### 특정 권한을 가지고 있을 경우 접근 제어
`hasRole` 메서드 이용
```java
// MANAGER 역할만 허용
return http
    .httpBasic(Customizer.withDefaults())
    .authorizeHttpRequests(c -> c.anyRequest().hasRole("MANAGER"))
    .build();
```

### N개 역할 중 하나를 가지고 있을 경우 접근 제어
`hasAnyRole` 메서드 이용

