package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.utils.UserDtoValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserDtoValidator userDtoValidator;
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Autowired
    public AdminController(UserDtoValidator userDtoValidator, RoleRepository roleRepository, UserService userService) {
        this.userDtoValidator = userDtoValidator;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping()
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.showAllUsers());
        return "users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", UserDto.toDto(userService.getUser(id)));
        model.addAttribute("allRoles", roleRepository.findAll());
        return "edit_user";
    }

    @PatchMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user") @Valid UserDto user, BindingResult bindingResult, Model model) {
        userDtoValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleRepository.findAll());
            return "edit_user";
        }
        userService.editUser(UserDto.toUser(user));
        return "redirect:/admin/users";
    }

    @DeleteMapping("/delete/{id}")
    public String removeUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin/users";
    }
}
