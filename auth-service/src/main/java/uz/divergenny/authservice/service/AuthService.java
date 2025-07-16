package uz.divergenny.authservice.service;

import uz.divergenny.authservice.entity.Role;
import uz.divergenny.authservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface AuthService {
    void saveUser(User user);

    String generateToken(String username, List<Role> roles);

    boolean validateToken(String token);

    Optional<User> getUser(String username);
}
