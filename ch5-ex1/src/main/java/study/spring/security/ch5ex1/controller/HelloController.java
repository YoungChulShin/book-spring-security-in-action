package study.spring.security.ch5ex1.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/hello")
  public String hello(Authentication authentication) {
    return (authentication != null)
        ? "Hello, " + authentication.getName()
        : "Hello, anonymous";
  }

}
