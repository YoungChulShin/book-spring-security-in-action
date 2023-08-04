package study.spring.security.auth.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.spring.security.auth.application.port.in.UserUseCase;
import study.spring.security.auth.application.port.out.OtpDataPort;
import study.spring.security.auth.application.port.out.UserDataPort;
import study.spring.security.auth.domain.User;

@Service
class UserService implements UserUseCase {

  private final PasswordEncoder passwordEncoder;
  private final UserDataPort userDataPort;
  private final OtpDataPort otpDataPort;

  public UserService(
      PasswordEncoder passwordEncoder,
      UserDataPort userDataPort,
      OtpDataPort otpDataPort) {
    this.passwordEncoder = passwordEncoder;
    this.userDataPort = userDataPort;
    this.otpDataPort = otpDataPort;
  }

  @Override
  public void addUser(User user) {
    user.updatePassword(passwordEncoder.encode(user.getPassword()));
    userDataPort.saveUser(user);
  }
}
