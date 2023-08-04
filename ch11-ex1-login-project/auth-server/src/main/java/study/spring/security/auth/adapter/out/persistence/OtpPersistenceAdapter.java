package study.spring.security.auth.adapter.out.persistence;

import java.util.Optional;
import org.springframework.stereotype.Component;
import study.spring.security.auth.application.port.out.OtpDataPort;
import study.spring.security.auth.domain.Otp;

@Component
class OtpPersistenceAdapter implements OtpDataPort {

  private final OtpJpaPersistenceAdapter jpaPersistenceAdapter;

  public OtpPersistenceAdapter(OtpJpaPersistenceAdapter jpaPersistenceAdapter) {
    this.jpaPersistenceAdapter = jpaPersistenceAdapter;
  }

  @Override
  public Optional<Otp> findOtpByUsername(String username) {
    return jpaPersistenceAdapter.findById(username);
  }
}
