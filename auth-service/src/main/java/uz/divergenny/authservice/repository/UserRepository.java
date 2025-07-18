package uz.divergenny.authservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.divergenny.authservice.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
