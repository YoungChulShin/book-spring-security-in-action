package study.spring.security.businessserver.authentication.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import study.spring.security.businessserver.authentication.OtpAuthentication;
import study.spring.security.businessserver.authentication.proxy.AuthenticationServerProxy;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

  private final AuthenticationServerProxy proxy;

  public OtpAuthenticationProvider(
      AuthenticationServerProxy proxy) {
    this.proxy = proxy;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String code = String.valueOf(authentication.getCredentials());

    boolean result = proxy.sendOtp(username, code);

    if (result) {
      return new OtpAuthentication(username, code);
    } else {
      throw new BadCredentialsException("Bad credentials.");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return OtpAuthenticationProvider.class.isAssignableFrom(authentication);
  }
}
