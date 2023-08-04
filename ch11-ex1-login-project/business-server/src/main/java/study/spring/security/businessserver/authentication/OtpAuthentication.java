package study.spring.security.businessserver.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class OtpAuthentication extends UsernamePasswordAuthenticationToken {

  public OtpAuthentication(Object principal, Object credentials) {
    super(principal, credentials);
  }
}
