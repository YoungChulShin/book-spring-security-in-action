package study.spring.security.ch8ex1.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
class ProjectConfig {

    @Bean
    fun userDetailService(): UserDetailsService {
        val manager = InMemoryUserDetailsManager()

        val user1 = User.withUsername("ycshin")
            .password("12345")
            .roles("ADMIN")
            .build()

        val user2 = User.withUsername("minj")
            .password("12345")
            .roles("MANAER")
            .build()

        manager.createUser(user1)
        manager.createUser(user2)

        return manager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic { }
            .authorizeHttpRequests {
                it.requestMatchers("/hello").hasRole("ADMIN")
                it.requestMatchers("/ciao").hasRole("MANAER")
                it.anyRequest().authenticated()
            }
            .build()
    }
}