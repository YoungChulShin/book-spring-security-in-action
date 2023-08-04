package study.spring.security.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Otp {

  @Id
  private String username;

  private String code;

  public Otp(String username, String code) {
    this.username = username;
    this.code = code;
  }

  protected Otp() { }

  public String getUsername() {
    return username;
  }

  public String getCode() {
    return code;
  }

  public void updateCode(String newCode) {
    this.code = newCode;
  }
}
