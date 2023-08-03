# 설명
formLogin에서 CSRF 토큰을 이용해서 POST 요청을 처리하는 방법을 알아본다. 

# 사용
## csrf token
GET 요청이 아닌 POST, PUT, DELETE 요청의 경우는, csrf가 활성화 되어 있다면 토큰 값을 전달하지 않으면 403 에러가 발생한다. 

해당 예제에는 로그인 화면을 통해서 페이지를 접근하게 되는데, 스프링에서 제공하는 로그인 화면의 개발자코드를 보면, hidden 타입으로 `_csrf` 값이 설정되어 있는것을 확인할 수 있다. 
```html
<input name="_csrf" type="hidden" value="V5kWApBulP3HnIYSV-Z66yY7O9YWyR14JgCkNx8zadEUnFiXb6gvNKRerM7qpL8kbstO2UQLFrch-ChVFGbCVS8CCugs-mD1">
```

## csrf 통과하기
제일 간단하게는 `SecurityFilterChain`에서 csrf를 비활성화하면 된다. 그게 아니라면 변경 요청에 대해서 csrf 토큰을 전달해야한다. 

이 예제에서는 `main.html` 화면에 hidden 타입의 input을 선언하고, 그 안에 설정된 csrs 값을 넣어주는 방법을 사용했다. 
```html
<form action="/product/add" method="post">
  <span>Name:</span>
  <span><input type="text" name="name" /></span>
  <span><button type="submit">Add</button></span>

  <!-- csrs 설정 -->
  <input type="hidden"
         th:name="${_csrf.parameterName}"
         th:value="${_csrf.token}"/>
</form>
```