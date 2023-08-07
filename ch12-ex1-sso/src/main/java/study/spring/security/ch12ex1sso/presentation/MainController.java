package study.spring.security.ch12ex1sso.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping("/")
  public String main() {
    return "main.html";
  }
}
