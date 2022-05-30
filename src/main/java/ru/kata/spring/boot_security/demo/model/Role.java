package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table (name = "authorities")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String authority;
    @Column
    private String Username;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Role() {
    }

    public Role(int id, String authority, String username, Set<User> users) {
        this.id = id;
        this.authority = authority;
        Username = username;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthority(String role) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}
