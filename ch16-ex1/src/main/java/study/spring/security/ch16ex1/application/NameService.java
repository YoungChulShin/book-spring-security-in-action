package study.spring.security.ch16ex1.application;

import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class NameService {

  @PreAuthorize("hasAuthority('write')")
  public String getName() {
    return "Fantastico";
  }

  // @PreAuthorize("#name == authentication.principal.username && hasAuthority('write')")
  @PreAuthorize("#name == authentication.principal.username")
  public List<String> secretNames(String name) {
    return secretNames.get(name);
  }

  private Map<String, List<String>> secretNames =
      Map.of(
          "ycshin", List.of("hello", "world"),
          "mjseo", List.of("java", "kotlin")
      );
}
