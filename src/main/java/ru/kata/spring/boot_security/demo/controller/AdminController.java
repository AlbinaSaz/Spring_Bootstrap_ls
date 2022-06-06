package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Service.UserServiceImt;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repozitory.RoleRepository;

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
    public String users(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "admin";
    }

    @GetMapping("new")
    public String newPerson(Model model) {
        List<Role> roles1 = roleRepository.findAll();
        model.addAttribute("roles", roles1);
        model.addAttribute("user", new User());
        return "new";
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
