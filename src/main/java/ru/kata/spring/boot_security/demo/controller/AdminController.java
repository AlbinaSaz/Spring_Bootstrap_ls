package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Service.UserServiceImt;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserServiceImt userService;
    private final RoleRepository roleRepository;


    @GetMapping()
    public String loadToUsersPage(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userTable", userService.allUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", userService.AllRoles());
        model.addAttribute("sessionUser", user);
        return "adminPage";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "addRole", required = false) ArrayList<String> userRole) {
        Set<Role> roleSet = new HashSet<>();
        if (userRole == null) {
            roleSet.add(roleRepository.findById(1).get());
            user.setRoles(roleSet);
            userService.save(user);
            return "redirect:/admin";
        }
        if (userRole.contains("ROLE_ADMIN")) {
            roleSet.add(roleRepository.findById(2).get());
            user.setRoles(roleSet);
        }
        if (userRole.contains("ROLE_USER")) {
            roleSet.add(roleRepository.findById(1).get());
            user.setRoles(roleSet);
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @PutMapping("/edit")
    public String update(@ModelAttribute("user") User user, Model model, @RequestParam(value = "addRole", required = false) ArrayList<String> userRole) {
        Set<Role> roles = new HashSet<>();
        if (userRole == null) {
            roles.add(roleRepository.findById(1).get());
            user.setRoles(roles);
            userService.save(user);
            return "redirect:/admin";
        }
        if (userRole.contains("ROLE_ADMIN")) {
            roles.add(roleRepository.findById(2).get());
            user.setRoles(roles);
        }
        if (userRole.contains("ROLE_USER")) {
            roles.add(roleRepository.findById(1).get());
            user.setRoles(roles);
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
