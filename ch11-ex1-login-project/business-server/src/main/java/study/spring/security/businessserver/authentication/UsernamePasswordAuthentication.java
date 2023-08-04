package study.spring.security.businessserver.authentication;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {

  public UsernamePasswordAuthentication(Object principal, Object credentials) {
    // setAuthenticated(false);
    super(principal, credentials);
  }

  public UsernamePasswordAuthentication(Object principal, Object credentials,
      Collection<? extends GrantedAuthority> authorities) {
    // super.setAuthenticated(true)
    super(principal, credentials, authorities);
  }
}
