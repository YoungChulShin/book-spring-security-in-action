package study.spring.security.businessserver.authentication.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;
import study.spring.security.businessserver.authentication.OtpAuthentication;
import study.spring.security.businessserver.authentication.UsernamePasswordAuthentication;

public class InitialAuthenticationFilter extends OncePerRequestFilter {

  private final AuthenticationManager manager;
  private final String signingKey;

  public InitialAuthenticationFilter(
      AuthenticationManager manager,
      @Value("${jwt.signing.key") String signingKey) {
    this.manager = manager;
    this.signingKey = signingKey;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String username = request.getHeader("username");
    String password = request.getHeader("password");
    String code = request.getHeader("code");

    if (code == null) { // opt 발급을 위한 인증 단계
      Authentication authentication = new UsernamePasswordAuthentication(username, password);
      manager.authenticate(authentication);
    } else {
      Authentication authentication = new OtpAuthentication(username, code);
      manager.authenticate(authentication);

      SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
      String jwt = Jwts.builder()
          .setClaims(Map.of("username", username))
          .signWith(key)
          .compact();
      response.setHeader("Authorization", jwt);
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    // login 경로에만 적용
    return !request.getServletPath().equals("/login");
  }

}
