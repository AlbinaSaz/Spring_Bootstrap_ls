package ru.kata.spring.boot_security.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ViewController {


    @GetMapping("/user")
    public String show(){
        return "user";}

    @GetMapping("/admin")
    public String adminView(){
        return "users";
    }
}