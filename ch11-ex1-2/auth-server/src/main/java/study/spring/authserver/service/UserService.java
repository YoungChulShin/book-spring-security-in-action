package study.spring.authserver.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.spring.authserver.domain.Otp;
import study.spring.authserver.domain.OtpRepository;
import study.spring.authserver.domain.User;
import study.spring.authserver.domain.UserRepository;
import study.spring.authserver.util.GenerateCodeUtil;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final OtpRepository otpRepository;

  public UserService(
      PasswordEncoder passwordEncoder,
      UserRepository userRepository,
      OtpRepository otpRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.otpRepository = otpRepository;
  }

  @Transactional
  public void addUser(User user) {
    user.updatePassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  @Transactional
  public void auth(User user) {
    User findUser = userRepository.findByUsername(user.getUsername())
        .orElseThrow(() -> new BadCredentialsException("Bad credentials"));

    if (passwordEncoder.matches(user.getPassword(), findUser.getPassword())) {
      renewOtp(findUser);
    } else {
      throw new BadCredentialsException("Bad credentials");
    }
  }

  public boolean check(Otp otpToValidate) {
    Otp findOtp = otpRepository.findByUsername(otpToValidate.getUsername()).orElse(null);
    if (findOtp == null) {
      return false;
    }

    return otpToValidate.getCode().equals(findOtp.getCode());
  }

  private void renewOtp(User user) {
    String code = GenerateCodeUtil.generateCode();

    Optional<Otp> findOtp = otpRepository.findByUsername(user.getUsername());

    if (findOtp.isPresent()) {
      Otp otp = findOtp.get();
      otp.renewCode(code);
    } else {
      Otp otp = new Otp(user.getUsername(), code);
      otpRepository.save(otp);
    }

    logger.info(">>OTP Code: {}", code);
  }
}
