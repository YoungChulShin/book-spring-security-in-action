package study.spring.security.ch10ex1csrf.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

  @GetMapping("/main")
  public String main() {
    return "main.html";
  }
}
