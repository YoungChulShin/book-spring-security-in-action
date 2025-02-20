# 저장소 설명
Spring Security Oauth를 이용해서 Authorization Server를 구현합니다. 

# 기능 설명
## Authorization code 인증
### 요청 순서
1. Authorization Code 요청
2. Access Token 요청

### openid 설정 확인
URL: http://localhost:8080/.well-known/openid-configuration

위 URL로 들어가면 접속 정보등이 포함된 정보를 확인할 수 있다. 
- authorization_endpoint: 인증 코드를 받기 위한 엔드포인트
- token_endpoint: 토큰을 받기 위한 엔드포인트
- grant_types_supported: 지원하는 grant type

### Authorization Code 요청
로그인 이후에 `redirect_uri` 로 `authorization code`가 전달된다. 

로그인 항목
- url: oauth2/authorize
- response_type: 응답 방법
- client_id: client id 값
- scope
- redirect_uri: authorization code를 받을 return url
- code_challenge: `PKCE` 사용을 위한 challenge 값
- code_challenge_method: verifier로부터 challenge를 생성하기 위한 해시코드 값

샘플
```
http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=https://www.manning.com/authorized&code_challenge=c9wKZNLr-8BJ2h3fWRNmT1NLGYcge6U587ZxNr3IbB0&code_challenge_method=S256
```

응답 샘플
```
https://www.manning.com/authorized?code=YxjsVLc5bFlwblauczfOwkcb63jxnyLQN93FXywTOiLtRgzABkSJ42-uJgwwgphu9CWpJOLiGK9RftISVVc2v2JyMWKdjkF9dqHpwBtbI6unM0JDC9WBbCj-xwavmK4F
```

### Access token 요청
`authorization code` 를 받으면 클라이언트는 `access token`을 요청할 수 있다. 

요청 항목
- url: /oauth2/token
- grant_type: 인증 타입
- client_id: client id
- redirect_uri
- code: authorization code
- code_verifier: PKCE verifier

특이사항
- 보안을 위해서 query parameter가 아니라 urlencode 로 전달한다. 

샘플
```
curl --location 'http://localhost:8080/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: ••••••' \
--header 'Cookie: JSESSIONID=4F519CD43758D947356A490D70513E15' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'code=US8C0vwpgNPRmudeNkLMvhyvd6Xrfspjob0PtvFOdAZeDL2XpwvZ1cQKOvc_mgarQqwRUJZLnQf71axOvUqJEQJZ5W-Qo_OaXxh0jHXiWtYv5CG3agpVXUY_D5t7vrEU' \
--data-urlencode 'code_verifier=OdcqyQcILUlhzCam2Jb2kxUrlKlqkFeki8MmUEhodlA' \
--data-urlencode 'client_id=client' \
--data-urlencode 'redirect_uri=https://www.manning.com/authorized'
```

응답 샘플
```
{
    "access_token": "eyJraWQiOiJjMmQ4ZTBhZi1iZGY2LTQzZGQtODU1Yi0xYWM1NTBmYzM5OTUiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ5Y3NoaW4iLCJhdWQiOiJjbGllbnQiLCJuYmYiOjE3NDAwMDkzODIsInNjb3BlIjpbIm9wZW5pZCJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJleHAiOjE3NDAwMDk2ODIsImlhdCI6MTc0MDAwOTM4MiwianRpIjoiYmJhNGE0ODMtMTIxZS00YTVhLTg1MGItMmE3MGYxM2YzZDNjIn0.gZU23fwmtjrtz0d65PeGAYKzDY8JIY9YHBKLG1sfFWC8upqurQ-03MfbKZI_qu8IAS_lK0fwY2l1Aficj4wz1Ltxpkv6drKSFGVFR4Kfj8mgrwe3-_E278GF7BGtjhz5dkJ6ZqVeDHBehLrVfE2__hcjMjTA9YhKw8seXO0RjBX7r78JpApwqDaYykGjKyx2KEiRwEF-Zbo19gy-lzdvursRvde4-Hmf91GtomME-FXGRC6nOApRz6x3KtOYapUPBob6edNfdlL-3EGK8cEJDsKSX12Voij_bRcDf10ACXgUomRgljJ4P_hrNE1erxpmP-EnSrydHtXjsE7uuWYqDQ",
    "refresh_token": "RNOML0J88ta0NGuiTzneD26VwIipeNnuSvv5XIydMc4kqF0rXyyUCpDhqJ80OHzfHE4yRuzFi09DQJ-rZV3kXoAt-t8D-DTeX4Ess2ZNB3hZnqrH4wgSztPHjdRgf6i_",
    "scope": "openid",
    "id_token": "eyJraWQiOiJjMmQ4ZTBhZi1iZGY2LTQzZGQtODU1Yi0xYWM1NTBmYzM5OTUiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ5Y3NoaW4iLCJhdWQiOiJjbGllbnQiLCJhenAiOiJjbGllbnQiLCJhdXRoX3RpbWUiOjE3NDAwMDkzNjYsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsImV4cCI6MTc0MDAxMTE4MiwiaWF0IjoxNzQwMDA5MzgyLCJqdGkiOiJlODNlMzE2My00Y2U2LTQ1OTktOTk2NC00ZGUxMjVmMDFlODQiLCJzaWQiOiJjdUxjVjN5R0kzMUJhMWlBRmZpZV9hMWhpRWd6RTIyOVZiOVZCajgwU1hRIn0.FWdGslx4vQILgCvoFaz04XrIqqNPHqIf2CeMEcbV9lj9FwgPGujOFOFo9NttFqz-ahawh1QWAuk5KfR18ecQgt08hpKTSSbrm-SLndXEgNndVQs3RzIZ030IawdyFJ0v_jDbHDGvMMhCEfbKr4yzPncbM_UtzMoE-YMny24PaRfgUxOeOICL_h1DNa-mJ9ODA-ccK7NPRixA2N1HAX1SxAomn9pR1YVoevKqkiLevAM68Fu9t1786YR12J9tdPJ9AY5Bw3-65a-uwvKDSJ9OJZHiF3qtwJDUFrwfzBX9Ee9ldej-ulKk0rEI8Z9tys2KBtplYfN1geHxmTQhDAwKdw",
    "token_type": "Bearer",
    "expires_in": 299
}
```


### PKCE verfier와 challenge
클라이언트의 요청이 올바른지 확인하기 위해서 사용되는 개념

verifier
- 32바이트 랜덤 값을 base64 인코딩
- access token을 요청할 때 클라이언트가 authorization server로 'verifier'와 '해시 알고리즘' 값을 전달한다. 

challenge
- verifier를 해시(예: SHA-256) 한 값
- authorization code를 요청할 때 클라이언트가 authorization server로 전달한다.


## Client Credentials 인증
### 요청 순서
`client credentials`는 별도의 순서 없이 `client id` + `client secret` 을 이용해서 요청하면 access token을 발급 받을 수 있다. 

### Access token 요청
요청 항목
- grant_type: client_credentials
- scope: openid

요청 샘플
```
curl --location 'http://localhost:8080/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: ••••••' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'scope=openid'
```

응답 샘플
```
{
    "access_token": "eyJraWQiOiI2NGIwNDVlOC1iYTgzLTRkZGItYWY0Ny1iYjc2ODQyZmNlZTQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJjbGllbnQiLCJhdWQiOiJjbGllbnQiLCJuYmYiOjE3NDAwNjMzODMsInNjb3BlIjpbIm9wZW5pZCJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJleHAiOjE3NDAwNjM2ODMsImlhdCI6MTc0MDA2MzM4MywianRpIjoiMTM4OTUyNGItYjkzMS00OWQwLWEzOGUtZmE5M2Y0ZTdjOTcyIn0.XXzfuSE7Lqmy-6Y1x1yqPgGqTk5B53Mfho4Krx-52O7d7ruva9UrRo-QLiexKZhLok8QHirIDEULtEoq88jwOmJzfFQAx9SvZ8jOB9e66esnEhNKlyOr3lR6IrjLsC5EuNILQARU4XNkSYFDF1zUq5DLIIn-_ZCt3egUwPE2B4xnqfuryPB9IvSyEWyW8zigkBTtSOvFo7cRoVcVf_tOMfdnBONI9BzvIi1Zipv8A8liefRx1mfJ4Dqwz3hDgyr0dEOWOXbWql74NNIitEFL5Von4-QUFtPNFJ8ttbui_RR94-_FO7W7tr3tagr-pIOkWdgzVTRaZfXD7B4aRbiYMA",
    "scope": "openid",
    "token_type": "Bearer",
    "expires_in": 299
}
```

## Token 비활성화
### 요청 정보
`oauth2/revoke` url에 관련 정보를 포함해서 전달한다. 

요청 항목
- token: 토큰 정보
- basic auth 헤더 