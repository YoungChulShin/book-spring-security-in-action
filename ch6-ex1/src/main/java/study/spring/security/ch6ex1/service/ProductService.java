package study.spring.security.ch6ex1.service;

import java.util.List;
import org.springframework.stereotype.Service;
import study.spring.security.ch6ex1.domain.Product;
import study.spring.security.ch6ex1.domain.ProductRepository;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> findAll() {
    return productRepository.findAll();
  }
}
