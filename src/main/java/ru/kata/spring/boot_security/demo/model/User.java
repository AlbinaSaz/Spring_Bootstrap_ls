package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Enter your name")
    @Size(min = 2, max = 15, message = "name length is not correct")
    @Column(nullable = false)
    private String username;
    @Column
    private String password;

    @Column
    private String city;
    @Min(value = 0, message = "age > 0")
    @Column
    private int age;
    @Column
    private boolean enabled;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade =
            {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(int id, String username, String password, String city, int age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.city = city;
        this.age = age;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String rolesString() {
        String a = getRoles().iterator().next().getAuthority();
        return a;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
