package study.spring.security.ch16ex2.config;

import java.io.Serializable;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import study.spring.security.ch16ex2.domain.Document;
import study.spring.security.ch16ex2.domain.DocumentRepository;

@Component
public class DocumentPermissionEvaluator implements PermissionEvaluator {

  private final DocumentRepository documentRepository;

  public DocumentPermissionEvaluator(DocumentRepository documentRepository) {
    this.documentRepository = documentRepository;
  }

  // targetDomainObject가 있어야하기 때문에, postAuthorize로 구현을 해야한다.
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

  // id를 기준으로 조회할 수 있기 때문에 preAuthorize를 사용할 수 있다.
  @Override
  public boolean hasPermission(
      Authentication authentication,
      Serializable targetId,
      String targetType,
      Object permission) {
    String code = targetId.toString();
    Document document = documentRepository.findDocument(code);

    String p = (String) permission;
    boolean admin = authentication.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals(p));

    return admin || document.owner().equals(authentication.getName());
  }
}
