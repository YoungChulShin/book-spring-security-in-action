package study.spring.security.ch6ex1.service;

import lombok.Getter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import study.spring.security.ch6ex1.domain.CustomUserDetails;

@Service
@Getter
public class AuthenticationProviderService implements AuthenticationProvider {

  private final JpaUserDetailsService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final SCryptPasswordEncoder sCryptPasswordEncoder;

  public AuthenticationProviderService(
      JpaUserDetailsService userDetailsService,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      SCryptPasswordEncoder sCryptPasswordEncoder) {
    this.userDetailsService = userDetailsService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.sCryptPasswordEncoder = sCryptPasswordEncoder;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    CustomUserDetails user = userDetailsService.loadUserByUsername(username);

    return switch (user.getUser().getAlgorithm()) {
      case BCRYPT -> checkPassword(user, password, bCryptPasswordEncoder);
      case SCRYPT -> checkPassword(user, password, sCryptPasswordEncoder);
    };
  }

  private Authentication checkPassword(
      CustomUserDetails user,
      String rawPassword,
      PasswordEncoder encoder) {
    if (encoder.matches(rawPassword, user.getPassword())) {
      return new UsernamePasswordAuthenticationToken(
          user.getUsername(),
          user.getPassword(),
          user.getAuthorities());
    } else {
      throw new BadCredentialsException("Bad credentials");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
