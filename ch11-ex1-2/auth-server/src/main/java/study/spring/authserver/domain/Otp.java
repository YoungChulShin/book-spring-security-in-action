package study.spring.authserver.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Otp {

  protected Otp() {
  }

  @Id
  private String username;
  private String code;

  public Otp(String username, String code) {
    this.username = username;
    this.code = code;
  }

  public String getUsername() {
    return username;
  }

  public String getCode() {
    return code;
  }

  public void renewCode(String code) {
    this.code = code;
  }
}
