package study.spring.security.ch10ex2cors.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

  private final Logger logger = LoggerFactory.getLogger(MainController.class);

  @GetMapping("/")
  public String main() {
    return "main.html";
  }

  @PostMapping("/test")
  @ResponseBody
  public String test() {
    logger.info("test method called");
    return "HELLO";
  }
}
