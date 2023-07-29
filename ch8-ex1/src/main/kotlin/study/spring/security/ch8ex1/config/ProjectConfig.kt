package study.spring.security.ch8ex1.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.RegexRequestMatcher

@Configuration
class ProjectConfig {

    @Bean
    fun userDetailService(): UserDetailsService {
        val manager = InMemoryUserDetailsManager()

        val user1 = User.withUsername("ycshin")
            .password("12345")
            .roles("ADMIN")
            .authorities("read", "premium")
            .build()

        val user2 = User.withUsername("minj")
            .password("12345")
            .roles("MANAER")
            .authorities("read")
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
                // 경로별 역할별 제한
                it.requestMatchers("/hello").hasRole("ADMIN")
                it.requestMatchers("/ciao").hasRole("MANAER")

                // Http 메서드와 경로 패턴 제한
                it.requestMatchers(HttpMethod.GET, "/a").authenticated()
                it.requestMatchers(HttpMethod.POST, "/a").permitAll()
                it.requestMatchers(HttpMethod.GET, "/a/b/**").authenticated()

                // 패턴 매칭
                it.requestMatchers(
                    RegexRequestMatcher.regexMatcher(".*/(us|uk|ca)+/(en|fr).*")
                ).authenticated()
                it.requestMatchers("/video/**").hasAuthority("premium")

                // 그 외는 거부
                it.anyRequest().denyAll()
            }
            .csrf {
                it.disable()
            }
            .cors {
                it.disable()
            }
            .build()
    }
}