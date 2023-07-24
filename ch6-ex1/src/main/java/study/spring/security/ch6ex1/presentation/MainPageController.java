package study.spring.security.ch6ex1.presentation;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import study.spring.security.ch6ex1.service.ProductService;

@Controller
public class MainPageController {

  private final ProductService productService;

  public MainPageController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/main")
  public String main(Authentication authentication, Model model) {
    model.addAttribute("username", authentication.getName());
    model.addAttribute("products", productService.findAll());

    return "main.html";
  }
}
