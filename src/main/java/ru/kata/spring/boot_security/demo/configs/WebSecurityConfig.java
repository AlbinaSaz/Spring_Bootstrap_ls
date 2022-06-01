package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;

    private final DataSource dataSource;


    public WebSecurityConfig(SuccessUserHandler successUserHandler, DataSource dataSource) {
        this.successUserHandler = successUserHandler;
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jdbcUserDetailsManager()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();

    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        UserDetails user = User.builder()
                .username("user")
                .password("$2a$05$5N9YIT7Kd98e5TqTF4xhf.J8Qa7l3Y5owveNdep4mVS5xdnrxSML2")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$05$EVJg8JR/GaadD3.JsIBj9uuNULOy9CcyZWtv.BxmIBkPF4nHQilR2")
                .roles("ADMIN")
                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(admin);

        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }

}