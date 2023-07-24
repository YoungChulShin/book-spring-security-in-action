# 설명
스프링 시큐리티를 이용해서 '로그인' 기능을 구현하는 애플리케이션입니다. 

기능 특징
- Algorithm을 별도로 분리해서, 사용자에게 설정된 알고리즘 타입에 따라서 AuthenticationProvider에서 검증을 다르게 합니다. 
- 웹 양식 인증을 사용합니다. 

# 사용
Algorithm 별 비밀번호 해시 분리
- 2개의 PasswordEncoder 구현체를 등록
   - `bCryptPasswordEncoder`
   - `sCryptPasswordEncoder`
- AuthenticationProvider 구현체에서 2개의 PasswordEncoder를 주입받아서, 사용자에게 등록된 알고리즘 타입에 따라서 match를 실행한다
   - `AuthenticationProviderService`

User 클래스와 UserDetails의 책임 분리
- User 클래스에 UserDetails를 함께 구현하면 코드의 복잡도가 증가할 수 있다. 이를 위해서 각각의 코드를 분리한다. 
- `User`
   - 사용자 정보를 저장하는 엔티티
- `CustomUserDetails`
   - UserDetails를 구현하는 클래스
   - 내부적으로 `User` 클래스 주입받아서 사용한다. 이를 통해서 User 클래스와 분리하면서, 기능을 구현할 수 있다.

CustomAuthenticationProvider에서 `supports` 처리
- UsernamePasswordAuthenticationToken와 같거나 하위클래스인지를 체크한다.
   ```java
   @Override
   public boolean supports(Class<?> authentication) {
     return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
   }
   ```
- authenticate() 메서드도 UsernamePasswordAuthenticationToken 객체를 리턴한다. 