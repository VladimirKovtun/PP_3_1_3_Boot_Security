package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import javax.validation.constraints.*;
import java.util.Set;

public class UserDto {
    private Long id;
    @NotEmpty(message = "Это поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    private String username;
    @NotBlank(message = "Это поле не может быть пустым и содержать пробелы")
    private String password;
    @NotBlank(message = "Это поле не может быть пустым и содержать пробелы")
    private String confirm;
    @Min(value = 10, message = "Значение должно быть больше 10")
    @Max(value = 100, message = "Значение должно быть меньше 100")
    private int age;
    @NotBlank(message = "Это поле не может быть пустым и содержать пробелы")
    @Email(message = "Введите почту в соотвествии со стандартом")
    private String eMail;
    private Set<Role> roles;

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setAge(user.getAge());
        userDto.seteMail(user.geteMail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setAge(userDto.getAge());
        user.seteMail(userDto.geteMail());
        user.setRoles(userDto.getRoles());
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
