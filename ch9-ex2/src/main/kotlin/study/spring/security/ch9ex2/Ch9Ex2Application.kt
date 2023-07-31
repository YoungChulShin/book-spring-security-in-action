package study.spring.security.ch9ex2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class Ch9Ex2Application

fun main(args: Array<String>) {
    runApplication<Ch9Ex2Application>(*args)
}
