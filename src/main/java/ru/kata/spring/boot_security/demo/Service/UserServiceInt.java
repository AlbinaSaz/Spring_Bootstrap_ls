package ru.kata.spring.boot_security.demo.Service;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserServiceInt {
    void save(User user);

    void updateUser(User user, int id);

    void deleteUser(int id);

    User findUser(int id);

    List<User> allUsers();

    User findByUsername(String username);
}
