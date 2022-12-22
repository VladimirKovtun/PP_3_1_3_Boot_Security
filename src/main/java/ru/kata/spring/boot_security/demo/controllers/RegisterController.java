package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping
public class RegisterController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public RegisterController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/register")
    public String createUser(@ModelAttribute("user") UserDto user, Model model) {
        model.addAttribute("allRoles", roleRepository.findAll());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserDto user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        } else if (userService.findByName(user.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "error.username", "Пользователь с таким именем существует!");
            return "register";
        } else if (!user.getPassword().equals(user.getConfirm())) {
            bindingResult.rejectValue("confirm", "error.confirm", "Пароли не совпадают!");
            return "register";
        }
        userService.addUser(UserDto.toUser(user));
        return "redirect:/login";
    }
}
