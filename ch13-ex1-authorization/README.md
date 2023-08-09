# 설명
OAuth2 authorization 서버를 구현합니다. 

# 사용
## 의존성 설정

## Authorization server 기능 활성화
`AuthServerConfig` 클래스
```java
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

}
```