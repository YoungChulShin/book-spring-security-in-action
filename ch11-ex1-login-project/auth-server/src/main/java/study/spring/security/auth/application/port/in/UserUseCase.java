package study.spring.security.auth.application.port.in;

import study.spring.security.auth.domain.Otp;
import study.spring.security.auth.domain.User;

public interface UserUseCase {

  void addUser(User user);

  void auth(User user);

  boolean check(Otp otpToValidate);

}
