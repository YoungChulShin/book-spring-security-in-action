package study.spring.security.businessserver.authentication.provider;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class OtpAuthentcation extends UsernamePasswordAuthenticationToken {

  public OtpAuthentcation(Object principal, Object credentials) {
    super(principal, credentials);
  }

  public OtpAuthentcation(Object principal, Object credentials,
      Collection<? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
  }
}
