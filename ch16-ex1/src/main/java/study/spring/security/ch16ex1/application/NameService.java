package study.spring.security.ch16ex1.application;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class NameService {

  @PreAuthorize("hasAuthority('write')")
  public String getName() {
    return "Fantastico";
  }

}
