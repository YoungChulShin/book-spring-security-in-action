package study.spring.security.businessserver.authentication.model;

public class User {

  private final String username;
  private final String password;
  private final String code;

  private User(String username, String password, String code) {
    this.username = username;
    this.password = password;
    this.code = code;
  }

  public static User getAuthInstance(String username, String password) {
    return new User(username, password, null);
  }

  public static User getOtpInstance(String username, String code) {
    return new User(username, null, code);
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getCode() {
    return code;
  }
}
