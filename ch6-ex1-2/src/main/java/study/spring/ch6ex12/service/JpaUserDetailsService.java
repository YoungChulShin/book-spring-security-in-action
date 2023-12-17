package study.spring.ch6ex12.service;

import java.util.function.Supplier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.spring.ch6ex12.domain.CustomUserDetails;
import study.spring.ch6ex12.domain.User;
import study.spring.ch6ex12.domain.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public JpaUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Supplier<UsernameNotFoundException> s =
        () -> new UsernameNotFoundException("Problem during authentication");

    User u = userRepository.findUserByUsername(username)
        .orElseThrow(s);

    return new CustomUserDetails(u);
  }
}
