package study.spring.security.auth.adapter.out.persistence;

import java.util.Optional;
import org.springframework.stereotype.Component;
import study.spring.security.auth.application.port.out.UserDataPort;
import study.spring.security.auth.domain.User;

@Component
class UserPersistenceAdapter implements UserDataPort {

  private final UserJpaPersistenceAdapter jpaPersistenceAdapter;

  public UserPersistenceAdapter(UserJpaPersistenceAdapter jpaPersistenceAdapter) {
    this.jpaPersistenceAdapter = jpaPersistenceAdapter;
  }

  @Override
  public Optional<User> findUserByName(String username) {
    return jpaPersistenceAdapter.findById(username);
  }
}
