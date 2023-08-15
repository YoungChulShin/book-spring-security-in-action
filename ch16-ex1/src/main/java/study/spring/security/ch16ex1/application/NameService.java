package study.spring.security.ch16ex1.application;

import org.springframework.stereotype.Service;

@Service
public class NameService {

  public String getName() {
    return "Fantastico";
  }

}
