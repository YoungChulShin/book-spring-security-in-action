# 설명
`UserDetails`와 `UserDetailsSerivce`를 직접 구현해서 메모리에서 사용자 정보를 관리하는 기능을 구현해봅니다.

# 사용
## `User` 클래스
`UserDetails` 인터페이스를 구현합니다. 

'UserDetails'는 UserDetailsService에서 사용자 정보를 읽어올 때, 응답 모델로 사용됩니다. 

## `InMemoryUserDetailsService` 클래스
`UserDetailsService` 인터페이스를 구현합니다. 

'List<UserDetails>'를 생성자로 받아서 멤버번수로 가지고 있습니다. 'loadUserByUsername' 메서드가 호출될 때 가지고 있는 사용자 정보 중에서 일치하는게 있는 반환합니다. 

## `ProjectConfig` 클래스
`UserDetailsService`의 구현체를 빈으로 등록합니다. 

'InMemoryUserDetailsService'를 구현체로 사용하고, 등록시점에 User 객체를 만들어서 등록합니다. 