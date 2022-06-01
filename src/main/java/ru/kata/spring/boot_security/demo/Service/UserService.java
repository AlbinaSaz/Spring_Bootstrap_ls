package ru.kata.spring.boot_security.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repozitory.UserRepository;
import java.util.List;

@Service
public class UserService implements UserServiceInt {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void save(User user) {
        user.setEnabled(true);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(user.getRoles());
        userRepository.save(user);
    }



    @Transactional
    @Override
    public void updateUser(User user, int id) {
        User user1 = userRepository.findById(id).get();
        user1.setUsername(user.getUsername());

    }

    @Transactional
    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);

    }

    @Transactional
    @Override
    public User findUser(int id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User %s не найден", username)));
    }
}
