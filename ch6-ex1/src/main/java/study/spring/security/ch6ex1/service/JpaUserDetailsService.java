package study.spring.security.ch6ex1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.spring.security.ch6ex1.domain.CustomUserDetails;
import study.spring.security.ch6ex1.domain.User;
import study.spring.security.ch6ex1.domain.UserRepository;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Problem during authentication!"));

    return new CustomUserDetails(user);
  }
}
