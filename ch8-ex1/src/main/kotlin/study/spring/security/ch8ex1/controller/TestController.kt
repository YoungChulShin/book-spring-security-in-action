package study.spring.security.ch8ex1.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @PostMapping("/a")
    fun postEndpointA(): String = "Works!"

    @GetMapping("/a")
    fun getEndpointA(): String = "Works!"

    @GetMapping("/a/b")
    fun getEndpointB(): String = "Works!"

    @GetMapping("/a/b/c")
    fun getEndpointC(): String = "Works!"

}