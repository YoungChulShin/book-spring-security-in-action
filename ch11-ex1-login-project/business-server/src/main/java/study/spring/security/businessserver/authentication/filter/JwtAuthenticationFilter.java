package study.spring.security.businessserver.authentication.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import study.spring.security.businessserver.authentication.UsernamePasswordAuthentication;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final String signingKey;

  public JwtAuthenticationFilter(
      @Value("${jwt.signing.key") String signingKey) {
    this.signingKey = signingKey;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // 토큰 검증
    String jwt = request.getHeader("Authorization");
    SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJwt(jwt)
        .getBody();

    String username = String.valueOf(claims.get("username"));

    // 권한 설정
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("user");
    var auth = new UsernamePasswordAuthentication(
        username,
        null,
        List.of(grantedAuthority));
    SecurityContextHolder.getContext().setAuthentication(auth);
    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return request.getServletPath().equals("/login");
  }
}
