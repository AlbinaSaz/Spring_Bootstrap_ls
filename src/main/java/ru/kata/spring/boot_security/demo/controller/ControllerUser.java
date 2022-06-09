package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Service.UserServiceImt;
import ru.kata.spring.boot_security.demo.model.User;

@RestController
@RequestMapping("/api")
public class ControllerUser {
    //
    @Autowired
    private UserServiceImt userService;

    @GetMapping("/user")
    public User getAuthorizedUser(){
        return  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
    }


}
