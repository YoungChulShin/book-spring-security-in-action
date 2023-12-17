package study.spring.ch6ex12.presentation;

import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import study.spring.ch6ex12.service.ProductService;

@Controller
public class MainPageController {

  private final ProductService productService;

  public MainPageController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/main")
  public String main(Authentication a, Model model, Principal principal) {
    model.addAttribute("username", a.getName());
    model.addAttribute("products", productService.findAll());

    return "main.html";
  }
}
