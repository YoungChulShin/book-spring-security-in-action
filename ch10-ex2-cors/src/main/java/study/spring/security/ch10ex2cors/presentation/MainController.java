package study.spring.security.ch10ex2cors.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
  // localhost:8080에 대한 교차 출처 요청을 허용한다.
  // http 응답 헤더에 'Access-Control-Allow-Origin: http://localhost:8080' 가 추가된다.
  // @CrossOrigin(value = "http://localhost:8080")
  public String test() {
    logger.info("test method called");
    return "HELLO";
  }
}
