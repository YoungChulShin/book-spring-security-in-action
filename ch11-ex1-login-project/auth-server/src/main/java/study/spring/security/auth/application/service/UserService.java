package study.spring.security.auth.application.service;

import java.util.Optional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.spring.security.auth.application.port.in.UserUseCase;
import study.spring.security.auth.application.port.out.OtpDataPort;
import study.spring.security.auth.application.port.out.UserDataPort;
import study.spring.security.auth.domain.Otp;
import study.spring.security.auth.domain.User;
import study.spring.security.auth.util.GenerateCodeUtil;

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
    // passwordEncoder로 rawPasword를 암호화해서 저장한다.
    user.updatePassword(passwordEncoder.encode(user.getPassword()));
    userDataPort.saveUser(user);
  }

  @Override
  public void auth(User targetUser) {
    User user = userDataPort.findUserByName(targetUser.getUsername())
        .orElseThrow(() -> new BadCredentialsException("Bad credentials"));

    if (passwordEncoder.matches(targetUser.getPassword(), user.getPassword())) {
      renewOtp(user);
      // send SMS to user with new otp code
    } else {
      throw new BadCredentialsException("Bad credentials");
    }
  }

  @Override
  public boolean check(Otp otpToValidate) {
    Optional<Otp> userOtp = otpDataPort.findOtpByUsername(otpToValidate.getUsername());
    if (userOtp.isEmpty()) {
      return false;
    }

    return userOtp.get().getCode().equals(otpToValidate.getCode());
  }

  private void renewOtp(User user) {
    String code = GenerateCodeUtil.generateCode();

    Optional<Otp> userOtp = otpDataPort.findOtpByUsername(user.getUsername());
    if (userOtp.isPresent()) {
      Otp otp = userOtp.get();
      otp.updateCode(code);
    } else {
      Otp otp = new Otp(user.getUsername(), code);
      otpDataPort.saveOtp(otp);
    }
  }
}
