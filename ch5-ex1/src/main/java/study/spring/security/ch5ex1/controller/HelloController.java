package study.spring.security.ch5ex1.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/hello")
  public String hello(Authentication authentication) {
    return (authentication != null)
        ? "Hello, " + authentication.getName()
        : "Hello, anonymous";
  }

  @GetMapping("/hola")
  public String hola() throws ExecutionException, InterruptedException {
    Callable<String> task = () -> {
      SecurityContext context = SecurityContextHolder.getContext();
      return context.getAuthentication().getName();
    };

    // DelegatingSecurityContextExecutorService를 이용해서 컨텍스트를 복사한다
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService = new DelegatingSecurityContextExecutorService(executorService);
    try {
      return "Hola, " + executorService.submit(task).get() + "!";
    } finally {
      executorService.shutdown();
    }
  }

}
