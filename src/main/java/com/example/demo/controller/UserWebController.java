package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserWebController {

    @Autowired
    private UserService userService;

    // Home page - show all users
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index";  // renders templates/index.html
    }

    // Show form to add user
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    // Handle form submit for adding user
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/";
    }

    // Show form to edit user
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        userService.getUserById(id).ifPresent(u -> model.addAttribute("user", u));
        return "edit-user";
    }

    // Handle update form
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("user") User user) {
        userService.updateUser(id, user);
        return "redirect:/";
    }

    // Delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
