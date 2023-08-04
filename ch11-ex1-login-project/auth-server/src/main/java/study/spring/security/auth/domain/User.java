package study.spring.security.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {

  @Id
  private String username;

  private String password;

  protected User() { }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void updatePassword(String password) {
    this.password = password;
  }
}
