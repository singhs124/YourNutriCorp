package com.example.NutriCorp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/h2-console/**", "/v1/**" ).permitAll()
//                        .anyRequest().authenticated()
//                )
//                .headers(headers -> headers.frameOptions().disable()) // Needed for H2 console
//                .csrf(csrf -> csrf.disable()); // Disable CSRF for H2 console
//
//        return http.build();

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
