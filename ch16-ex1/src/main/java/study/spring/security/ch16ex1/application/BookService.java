package study.spring.security.ch16ex1.application;

import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import study.spring.security.ch16ex1.domain.Employee;

@Service
public class BookService {

  private Map<String, Employee> records = Map.of(
      "ycshin",
      new Employee(
          "youngchulshin",
          List.of("fastapi", "kubernetes"),
          List.of("accountant", "reader")),
      "mjseo",
      new Employee(
          "minjung",
          List.of("msa"),
          List.of("researcher"))
  );

  @PostAuthorize("returnObject.roles().contains('reader')")
  public Employee getBookDetails(String name) {
    return records.get(name);
  }
}
