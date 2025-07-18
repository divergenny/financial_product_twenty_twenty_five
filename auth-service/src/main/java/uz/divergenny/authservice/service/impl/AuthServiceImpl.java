package uz.divergenny.authservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.divergenny.authservice.entity.Role;
import uz.divergenny.authservice.entity.Status;
import uz.divergenny.authservice.entity.User;
import uz.divergenny.authservice.repository.RoleRepository;
import uz.divergenny.authservice.repository.UserRepository;
import uz.divergenny.authservice.service.AuthService;
import uz.divergenny.authservice.service.JwtService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthServiceImpl(UserRepository repository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }

    public void saveUser(User user) {
        Optional<Role> role = roleRepository.findByName("USER");
        if (role.isEmpty()) {
            throw new RuntimeException("В БД не найдена роль с именем USER");
        }
        List<Role> roles = new ArrayList<>();
        roles.add(role.get());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setRoles(roles);
        repository.save(user);
    }

    public String generateToken(String username, List<Role> roles) {
        return jwtService.generateToken(username, roles);
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    @Override
    public Optional<User> getUser(String username) {
        return repository.findByUsername(username);
    }

}
