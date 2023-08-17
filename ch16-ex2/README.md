# 설명
전역 메서드에 권한을 체크할 때 SpEL로 적용이 어려운 복잡한 케이스에 대응하는 법을 알아봅니다. 

# 사용
## PermissionEvaluator 인터페이스
`PermissionEvaluator` 인터페이스의 구현체를 이용해서 접근 케이스의 체크 로직을 분리할 수 있다. 
```java
public interface PermissionEvaluator extends AopInfrastructureBean {
    // targetDomainObject을 포함한다. 
    // 따라서 PostAuthorize에서 returnObject를 여기에 전달해서 검증할 때 사용할 수 있을것 같다.
	boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission);

    // id를 가지고 있기 때문에, preAuthorize에서 id를 기준으로 리소스를 조회해서 검증할 수 있다.
	boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission);
}
```

## DocumentPermissionEvaluator 클래스
이 프로젝트에서 사용되는 `PermissionEvaluator`의 구헨체 클래스.

targetId를 전달 받아서 처리하는 hasPermission의 경우는 별도의 Repository를 이용해서 데이터를 조회해서 permission 체크를 한다. 