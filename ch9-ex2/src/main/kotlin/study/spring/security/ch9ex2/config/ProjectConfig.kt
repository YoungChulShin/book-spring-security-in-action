package study.spring.security.ch9ex2.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
class ProjectConfig(
    private val staticKeyAuthenticationFilter: StaticKeyAuthenticationFilter,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .addFilterAt(
                staticKeyAuthenticationFilter,
                BasicAuthenticationFilter::class.java)
            .authorizeHttpRequests {
                it.anyRequest().permitAll()
            }

        return http.build()
    }
}