package study.spring.security.auth.application.port.in;

import study.spring.security.auth.domain.User;

public interface UserUseCase {

  void addUser(User user);

}
