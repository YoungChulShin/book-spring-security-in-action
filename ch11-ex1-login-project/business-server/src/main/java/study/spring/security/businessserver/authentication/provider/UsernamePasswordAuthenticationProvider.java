package study.spring.security.businessserver.authentication.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import study.spring.security.businessserver.authentication.UsernamePasswordAuthentication;
import study.spring.security.businessserver.authentication.proxy.AuthenticationServerProxy;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

  private final AuthenticationServerProxy proxy;

  public UsernamePasswordAuthenticationProvider(
      AuthenticationServerProxy proxy) {
    this.proxy = proxy;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = String.valueOf(authentication.getCredentials());

    proxy.sendAuth(username, password);

    return new UsernamePasswordAuthentication(username, password);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthentication.class.isAssignableFrom(authentication);
  }
}
