package study.spring.security.ch16ex1.domain;

import java.util.List;

public record Employee(
    String name,
    List<String> books,
    List<String> roles
) { }
