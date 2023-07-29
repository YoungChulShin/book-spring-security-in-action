package study.spring.security.ch8ex1.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class VideoController {

    @GetMapping("/video/{country}/{language}")
    fun video(
        @PathVariable country: String,
        @PathVariable language: String,
    ): String {
        return "Video allowed for $country $language"
    }
}