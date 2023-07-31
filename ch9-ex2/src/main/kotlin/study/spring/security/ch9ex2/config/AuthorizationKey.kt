package study.spring.security.ch9ex2.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "authorization")
class AuthorizationKey(
    val key: String,
)