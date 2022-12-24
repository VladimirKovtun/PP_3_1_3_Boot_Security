package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepositories;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepositories, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepositories = roleRepositories;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    @Override
    public void addUser(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of(roleRepositories.getByName("ROLE_USER")));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> showAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id : " + id + " not found"));
    }

    @Transactional
    @Override
    public void editUser(User user) {
        User byId = userRepository.findById(user.getId()).orElseThrow();
        byId.toUser(user);
        userRepository.save(byId);
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByUsername(name);
    }
}
