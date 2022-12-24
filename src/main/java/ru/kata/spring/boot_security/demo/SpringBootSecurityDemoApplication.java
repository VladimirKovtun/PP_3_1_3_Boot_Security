package ru.kata.spring.boot_security.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Arrays;
import java.util.Set;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunnerBean(UserService userService, RoleRepository roleRepositories) {


        return (args) -> {
            Role roleUser = new Role("ROLE_USER");
            Role roleAdmin = new Role("ROLE_ADMIN");
            roleRepositories.save(roleUser);
            roleRepositories.save(roleAdmin);
            Arrays.asList(new User("Имя20", "2020", 20, "mail20@mail.ru", Set.of(roleUser)),
                            new User("Имя65", "6565", 65, "mail65@mail.ru", Set.of(roleUser)),
                            new User("Имя31", "3131", 31, "mail31@mail.ru", Set.of(roleUser)),
                            new User("админ2", "админ2", 45, "mail45@mail.ru", Set.of(roleAdmin)),
                            new User("Имя27", "2727", 27, "mail27@mail.ru", Set.of(roleUser)),
                            new User("админ1", "админ1", 39, "mail39@mail.ru", Set.of(roleUser, roleAdmin)))
                    .forEach(userService::addUser);
        };
    }
}
