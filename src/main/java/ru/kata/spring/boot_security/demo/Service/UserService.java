package ru.kata.spring.boot_security.demo.Service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void save(User user);

    void updateUser(User user);

    void deleteUser(Integer id);

    User findUser(int id);

    List<User> allUsers();

    User findByUsername(String username);
}
