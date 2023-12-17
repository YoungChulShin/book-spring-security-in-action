package study.spring.ch6ex12.service;

import java.util.List;
import org.springframework.stereotype.Service;
import study.spring.ch6ex12.domain.Product;
import study.spring.ch6ex12.domain.ProductRepository;

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
