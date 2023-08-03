package study.spring.security.ch10ex1csrf.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping("/main")
  public String main() {
    return "main.html";
  }
}
