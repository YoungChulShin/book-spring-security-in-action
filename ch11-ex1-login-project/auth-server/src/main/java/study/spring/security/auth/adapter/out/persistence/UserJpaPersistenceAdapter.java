package study.spring.security.auth.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import study.spring.security.auth.domain.User;

interface UserJpaPersistenceAdapter extends JpaRepository<User, String> {

}
