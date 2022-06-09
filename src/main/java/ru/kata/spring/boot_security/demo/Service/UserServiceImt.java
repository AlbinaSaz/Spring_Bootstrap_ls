package ru.kata.spring.boot_security.demo.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImt implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void saveUser() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role().setId(1).setAuthority("ROLE_ADMIN"));
        User user = new User(1, "admin", bCryptPasswordEncoder.encode("admin"), "Moscow", 15);
        user.setEnabled(true);
        user.setRoles(roles);
        userRepository.save(user);

        Set<Role> roles2 = new HashSet<>();
        roles2.add(new Role().setId(2).setAuthority("ROLE_USER"));
        User user1 = new User(2, "user", bCryptPasswordEncoder.encode("user"), "Moscow", 20);
        user1.setEnabled(true);
        user1.setRoles(roles2);
        userRepository.save(user1);
    }


    @Transactional
    public List<Role> AllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public Role findById(Integer id) {

        Optional<Role> roleOptional = roleRepository.findById(id);
        return roleOptional.orElse(null);
    }

    @Transactional
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }

    @Transactional
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }


    @Transactional
    @Override
    public void save(User user) {
        user.setEnabled(true);
//        Set<Role> roleSet = (Set<Role>) user.getAuthorities();
//        user.setCity(user.getCity());
//        user.setAge(user.getAge());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setRoles(roleSet);
        userRepository.save(user);
    }


    @Transactional
    @Override
    public void updateUser(User user) {

        if (user.getPassword().equals(findUser(user.getId()).getPassword())) {
            user.setPassword(findUser(user.getId()).getPassword());
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);

}

    @Transactional
    @Override
    public void deleteUser(Integer id) {
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
