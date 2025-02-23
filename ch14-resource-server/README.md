# 저장소 설명
Spring Security Oauth를 이용해서 Resource Server를 구현합니다.

# 기능 설명
## Resource server 인증
`SecurityFilterChain` 에서 `oauth2ResourceServer` 옵션을 설정. 
### Non-Opaque 토큰
JWT 토큰을 이용해서 정보를 가져오는 방법. Token을 validation 하기 위한 JWK 설정을 해줘야한다. 

### Opaque 토큰
Access Token을 받으면, 설정된 introspectionUri를 호출해서 Token을 검증한다. 

이때 resource server가 authorization server를 호출하기 때문에, authorization server에 client 정보가 등록되어 있어야한다. 


