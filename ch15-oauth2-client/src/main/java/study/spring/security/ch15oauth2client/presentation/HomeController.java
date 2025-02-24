package study.spring.security.ch15oauth2client.presentation;

import java.security.Principal;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String home(
      @AuthenticationPrincipal OAuth2User oAuth2User) {
    String name = oAuth2User.getName();
    oAuth2User.getAttributes().entrySet()
        .forEach(e -> System.out.println(e.getKey() + " : " + e.getValue()));

    return "index.html";
  }

}
