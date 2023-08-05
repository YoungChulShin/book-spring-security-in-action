package study.spring.security.businessserver.authentication.proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import study.spring.security.businessserver.authentication.model.User;

@Component
public class AuthenticationServerProxy {

  private final RestTemplate rest;
  private final String baseUrl;

  public AuthenticationServerProxy(
      RestTemplate rest,
      @Value("${auth.server.base.url}") String baseUrl) {
    this.rest = rest;
    this.baseUrl = baseUrl;
  }

  public void sendAuth(String username, String password) {
    String url = baseUrl + "/user/auth";

    var body = User.getAuthInstance(username, password);
    var request = new HttpEntity<>(body);

    this.rest.postForEntity(url, request, Void.class);
  }

  public boolean sendOtp(String username, String code) {
    String url = baseUrl + "/user/check";

    var body = User.getOtpInstance(username, code);
    var request = new HttpEntity<>(body);

    var response = this.rest.postForEntity(url, request, Void.class);

    return response.getStatusCode().equals(HttpStatus.OK);
  }
}
