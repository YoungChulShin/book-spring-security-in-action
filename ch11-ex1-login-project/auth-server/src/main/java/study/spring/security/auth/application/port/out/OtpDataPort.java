package study.spring.security.auth.application.port.out;

import java.util.Optional;
import study.spring.security.auth.domain.Otp;

public interface OtpDataPort {

  Optional<Otp> findOtpByUsername(String username);
}
