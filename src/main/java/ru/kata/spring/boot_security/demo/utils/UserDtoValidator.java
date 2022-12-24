package ru.kata.spring.boot_security.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Objects;

@Component
public class UserDtoValidator implements Validator {
    private final UserRepository userRepository;

    @Autowired
    public UserDtoValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Objects.equals(UserDto.class, clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;
        if (userDto.getId() != null && userDto.getRoles().isEmpty()) {
            errors.rejectValue("roles", "", "Поле не может быть пустым!");
        } else if (userDto.getId() == null && userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "Пользователь с таким именем существует!");
        } else if (userDto.getId() == null && !userDto.getPassword().equals(userDto.getConfirm())) {
            errors.rejectValue("confirm", "", "Пароли не совпадают!");
        }
    }
}
