package study.spring.security.ch8ex1.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/hello")
    fun hello() = "hello"

    @GetMapping("/ciao")
    fun ciao() = "Ciao!"
}