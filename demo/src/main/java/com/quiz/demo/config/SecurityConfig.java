package com.quiz.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                // ✅ PUBLIC QUIZ FLOW (NO LOGIN EVER)
                .requestMatchers(
                    "/",
                    "/start",
                    "/submit",
                    "/question/**",
                    "/previous",
                    "/result",
                    "/result-review",
                    "/css/**"
                ).permitAll()

                // 🔒 ADMIN ONLY
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // ❗ everything else requires auth
                .anyRequest().authenticated()
            )

            // 🔐 ADMIN LOGIN ONLY
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/admin", true)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/start")
                .permitAll()
            );

        return http.build();
    }

    // 🔑 THIS WAS MISSING — REQUIRED
    @Bean
    public InMemoryUserDetailsManager users() {
        return new InMemoryUserDetailsManager(
            User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build()
        );
    }
}
