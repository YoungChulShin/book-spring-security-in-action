package study.spring.security.ch12ex1sso.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.login.oauth2")
public record OAuth2ClientInfo(
    String clientId,
    String clientSecret
) {

}
