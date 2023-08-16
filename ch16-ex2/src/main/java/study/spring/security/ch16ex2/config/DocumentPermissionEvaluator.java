package study.spring.security.ch16ex2.config;

import java.io.Serializable;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import study.spring.security.ch16ex2.domain.Document;

public class DocumentPermissionEvaluator implements PermissionEvaluator {

  @Override
  public boolean hasPermission(
      Authentication authentication,
      Object targetDomainObject,
      Object permission) {
    Document document = (Document) targetDomainObject;
    String p = (String) permission;

    boolean admin = authentication.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals(p));

    return admin ||
        document.owner().equals(authentication.getName());
  }

  @Override
  public boolean hasPermission(
      Authentication authentication,
      Serializable targetId,
      String targetType,
      Object permission) {
    return false;
  }
}
