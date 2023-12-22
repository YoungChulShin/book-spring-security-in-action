package study.spring.authserver.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

class GenerateCodeUtilTest {

  @Test
  void randomTest() {
    Random random = new Random();

    for (int i = 0; i < 10; i++) {
      System.out.println(random.nextInt());
    }
  }

  @Test
  void randomWithSeedTest() {
    Random random = new Random(10L);

    for (int i = 0; i < 10; i++) {
      System.out.println(random.nextInt());
    }
  }

  // 동일한 seed 값을 같는 Random은 같은 패턴의 값을 생성한다.
  @Test
  void randomWithLoopTest() {
    for (int i = 0; i < 10; i++) {
      Random random = new Random(100L);
      for (int j = 0; j < 10; j++) {
        System.out.println(random.nextInt());
      }
    }
  }
}