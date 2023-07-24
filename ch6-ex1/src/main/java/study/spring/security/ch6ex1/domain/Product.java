package study.spring.security.ch6ex1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import study.spring.security.ch6ex1.domain.enums.Currency;

@Entity
@Getter
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private double price;

  @Enumerated(EnumType.STRING)
  private Currency currency;

}
