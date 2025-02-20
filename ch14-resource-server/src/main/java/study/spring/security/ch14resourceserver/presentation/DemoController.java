package study.spring.security.ch14resourceserver.presentation;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

  @GetMapping("/demo")
  public String demo(Principal principal) {
    return principal.getName();
  }

}
