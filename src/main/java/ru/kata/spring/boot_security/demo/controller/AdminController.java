package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Service.UserService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping()
    public String users(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "admin";
    }

    @GetMapping("new")
    public String newPerson(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findUser(id));
        return "findUser";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findUser(id));
        return "update";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateUser(user, id);
        return "redirect:/admin";
    }


    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
