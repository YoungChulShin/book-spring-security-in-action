package study.spring.security.auth.adapter.in.web;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.spring.security.auth.application.port.in.UserUseCase;
import study.spring.security.auth.domain.Otp;
import study.spring.security.auth.domain.User;

@RestController
class AuthController {

  private final UserUseCase userUseCase;

  public AuthController(UserUseCase userUseCase) {
    this.userUseCase = userUseCase;
  }

  @PostMapping("/user/add")
  public String addUser(@RequestBody User user) {
    userUseCase.addUser(user);

    return "Success";
  }

  @PostMapping("/user/auth")
  public void auth(@RequestBody User user) {
    userUseCase.auth(user);
  }

  @PostMapping("/user/check")
  public void check(@RequestBody Otp otp, HttpServletResponse response) {
    if (userUseCase.check(otp)) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
  }

}
