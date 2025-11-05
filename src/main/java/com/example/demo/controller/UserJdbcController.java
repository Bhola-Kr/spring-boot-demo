package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserJdbcService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jdbc")
public class UserJdbcController {

    private final UserJdbcService userJdbcService;

    public UserJdbcController(UserJdbcService userJdbcService) {
        this.userJdbcService = userJdbcService;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("users", userJdbcService.getAllUsers());
        return "index"; // reuse same Thymeleaf view
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userJdbcService.addUser(user);
        return "redirect:/jdbc/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        userJdbcService.getUserById(id).ifPresent(u -> model.addAttribute("user", u));
        return "edit-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("user") User user) {
        userJdbcService.updateUser(id, user);
        return "redirect:/jdbc/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userJdbcService.deleteUser(id);
        return "redirect:/jdbc/";
    }
}
