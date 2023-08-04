package study.spring.security.auth.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class GenerateCodeUtil {

  private GenerateCodeUtil() { }

  public static String generateCode() {
    try {
      SecureRandom random = SecureRandom.getInstanceStrong();
      // 0~8999의 임의의 값을 만들고, 1000을 더해서 1000~9999의 4자리 값을 얻는다.
      return String.valueOf(random.nextInt(9000) + 1000);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Problem when generating the random code.");
    }
  }
}
