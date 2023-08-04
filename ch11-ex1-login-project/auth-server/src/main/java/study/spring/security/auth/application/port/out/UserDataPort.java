package study.spring.security.auth.application.port.out;

import java.util.Optional;
import study.spring.security.auth.domain.User;

public interface UserDataPort {

  Optional<User> findUserByName(String username);

  User saveUser(User user);

}
