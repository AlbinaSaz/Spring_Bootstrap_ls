package ru.kata.spring.boot_security.demo.repozitory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
