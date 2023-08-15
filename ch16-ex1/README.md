# 설명
전역 메서드 보안 관련된 코드를 작성해봅니다. 

# 사용
`@PreAuthorize` 애노테이션을 이용해서 전역 메서드에 보안 코드를 추가할 수 있습니다. 

'@PreAuthorize'은 표현식을 받을 수 있는데, 몇가지 예시를 아래에 설명합니다. 

## hasAuthority
인증된 사용자 정보를 기준으로 메서드를 실행할 권한이 있는지를 체크합니다. 

아래 코드는 'write' 권한을 가지고 있는 사용자만 메서드를 실행할 수 있습니다. 
```java
@PreAuthorize("hasAuthority('write')")
public String getName() {
    return "Fantastico";
}
```

hasAuthority외에도 아래 메서드를 사용할 수 있습니다. 
- hasAnyAuthority()
- hasRole()
- hasAnyRole()

## authentication.principal.username
인증이 완료되면 `SecurityContextHolder`의 `Authentication`에 관련 정보가 저장됩니다. Authentication은 `Principal` 정보를 가지고 있는데, 이를 이용해서 사용자 이름을 기준으로 일치 여부를 검사할 수 있습니다. 

아래 코드는 메서드의 파라미터로 입력받은 name이 실제로 로그인한 사용자의 이름과 일치하는지를 비교합니다. 
```java
@PreAuthorize("#name == authentication.principal.username")
public List<String> secretNames(String name) {
    return secretNames.get(name);
}
```

## 조건 조합
앞에서 설명한 2개의 권한을 조합하는 것도 가능합니다. `||(or 조건)`, `&&(and 조건)` 을 사용할 수 있습니다. 

아래처럼 코드를 작성할 수 있습니다. 
```java
@PreAuthorize("#name == authentication.principal.username && hasAuthority('write')")
public List<String> secretNames(String name) {
    return secretNames.get(name);
}
```