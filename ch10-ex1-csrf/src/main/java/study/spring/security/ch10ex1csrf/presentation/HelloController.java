package study.spring.security.ch10ex1csrf.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @PostMapping("/hello")
  public String hello() {
    return "Hello!";
  }
}
