package com.example.SchoolClubManagementSystem.Security;

import com.example.SchoolClubManagementSystem.User.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home", "/access-denied").permitAll()
                        .requestMatchers("/sign-in", "/sign-up", "/submit").anonymous()
                        .requestMatchers("/clubs/my-club", "/clubs/create", "/clubs/submit", "/posts/**").hasAuthority("ADMIN")
                        .requestMatchers("/clubs/submit-join-club/**").hasAuthority("USER")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/sign-in")
                        .usernameParameter("usernameOrEmail")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/access-denied");
                        })
                )
                .logout((logout) -> logout.permitAll());
        return http.build();
    }
}