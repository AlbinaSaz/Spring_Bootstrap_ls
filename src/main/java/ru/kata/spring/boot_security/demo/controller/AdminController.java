package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Service.UserServiceImt;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminController {
    private final UserServiceImt userService;
    private final RoleRepository roleRepository;

    @GetMapping("/admin/roles")
    public List<Role> getRoles(){
        List<Role> roles  =roleRepository.findAll();
        System.out.println(roles);
        return roles;

    }

    @GetMapping("/admin/users")
    public List<User> getAllUsers (){
      return  userService.allUsers();
    }

    @GetMapping ("/admin/users/{id}")
    public User getUser (@PathVariable int id){
       return userService.findUser(id);
    }


    @PostMapping(("/admin/users"))
    public ResponseEntity<User> saveUser (@Valid @RequestBody User user){
         System.out.println("POST REQUEST  " + user);
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/admin/users")
    public ResponseEntity <User> editUser (@RequestBody User user){
        System.out.println(user);
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping ("/admin/users/{id}")
    public void deleteUserById (@PathVariable("id") int id){
        User user = userService.findUser(id);
        System.out.println("delete "+ user);
        userService.deleteUser(id);
    }
}
//