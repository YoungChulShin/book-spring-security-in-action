package study.spring.security.ch16ex1.presentation;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.spring.security.ch16ex1.application.NameService;

@RestController
public class HelloController {

  private final NameService nameService;

  public HelloController(NameService nameService) {
    this.nameService = nameService;
  }

  @GetMapping("/hello")
  public String hello() {
    return "Hello, " + nameService.getName();
  }

  @GetMapping("/secret/names/{name}")
  public List<String> names(
      @PathVariable String name
  ) {
    return nameService.secretNames(name);
  }
}
