package study.spring.security.ch14authorizationserver.generator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class VerifierGenerator {

  public static void main(String[] args) throws NoSuchAlgorithmException {
    String verifier = generateVerifier();
    String challenge = generateChallenge(verifier);

    System.out.printf("verifier: %s, challenge: %s%n", verifier, challenge);
  }

  private static String generateChallenge(String verifier) throws NoSuchAlgorithmException {
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    byte[] digested = messageDigest.digest(verifier.getBytes());
    return Base64.getUrlEncoder()
            .withoutPadding()
                .encodeToString(digested);
  }

  private static String generateVerifier() {
    SecureRandom secureRandom = new SecureRandom();
    byte [] code = new byte[32];
    secureRandom.nextBytes(code);
    return Base64.getUrlEncoder()
        .withoutPadding()
        .encodeToString(code);
  }

}
