package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping()
public class AdminController {

    private final RoleRepository roleRepository;
    private final UserService userService;

    @Autowired
    public AdminController(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.showAllUsers());
        return "users";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", UserDto.toDto(userService.getUser(id)));
        model.addAttribute("allRoles", roleRepository.findAll());
        return "edit_user";
    }

    @PatchMapping("/admin/{id}")
    public String updateUser(@ModelAttribute("user") @Valid UserDto user, BindingResult bindingResult, Model model) {
        if (user.getRoles().isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleRepository.findAll());
            if (user.getRoles().isEmpty()) {
                bindingResult.rejectValue("roles", "error.roles", "Поле не может быть пустым!");
            }
            return "edit_user";
        }
        userService.editUser(UserDto.toUser(user));
        return "redirect:/users";
    }

    @DeleteMapping("/admin/{id}")
    public String removeUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/users";
    }
}
