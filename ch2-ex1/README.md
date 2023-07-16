# 저장소 설명
스프링 시큐리티를 이용한 프로그램을 구성하고 실행해본다.

별도의 설정은 하지 않고, 스프링 시큐리티가 제공하는 기본 구성을 사용한다. 

# 사용
## 사용자 정보
사용자 명은 `user`가 기본 값이다. 

비밀번호는 프로그램이 실행될 때 console 창에 관련 정보가 표시된다.
```
Using generated security password: 8a73870d-d75c-4f7d-950c-916d715567c3
```

## curl을 이용한 호출
`-u` 옵션 사용
```
curl -u user:{{password}} -v http://localhost:8080/hello
```

`Authorization` Header 사용
```
// base64 값 구하기
echo -n user:{{password}} | base64

// curl 호출
curl -H "Authorization: Basic {{base64 encoded}}" -v http://localhost:8080/hello
```