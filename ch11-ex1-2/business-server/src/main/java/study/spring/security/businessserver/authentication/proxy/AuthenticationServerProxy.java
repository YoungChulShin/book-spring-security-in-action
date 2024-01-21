package study.spring.security.businessserver.authentication.proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationServerProxy {

  private final RestTemplate restTemplate;
  private final String baseUrl;

  public AuthenticationServerProxy(
      RestTemplate restTemplate,
      @Value("${auth.server.base.url}") String baseUrl
  ) {
    this.restTemplate = restTemplate;
    this.baseUrl = baseUrl;
  }

  public void sendAuth(String username, String password) {
    String url = baseUrl + "/user/auth";
    User body = new User(username, password);

    var request = new HttpEntity<>(body);
    restTemplate.postForEntity(url, request, Void.class);
  }

  public boolean sendOTP(String username, String code) {
    String url = baseUrl + "/otp/check";
    User body = new User(username, code);

    var request = new HttpEntity<>(body);
    var response = restTemplate.postForEntity(url, request, Void.class);

    return response.getStatusCode().equals(HttpStatus.OK);
  }


}
