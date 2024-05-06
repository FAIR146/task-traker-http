package com.example.tasktrackerhttp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/putTask").authenticated()
                                .requestMatchers("/putEpic").authenticated()
                                .requestMatchers("/putSubTask").authenticated()
                                .requestMatchers("/deleteEpicById").authenticated()
                                .requestMatchers("/deleteTaskById").authenticated()
                                .requestMatchers("/deleteSubTaskById").authenticated()
                                .requestMatchers("/getTaskById").authenticated()
                                .requestMatchers("/getEpicById").authenticated()
                                .requestMatchers("/getSubTaskById").authenticated()
                                .requestMatchers("/updateEpic").authenticated()
                                .requestMatchers("/updateTask").authenticated()
                                .requestMatchers("/updateSubTask").authenticated()
                                .requestMatchers("/getAllCreatedEpicsByUser").authenticated()
                                .requestMatchers("/getAllCreatedTasksByUser").authenticated()
                                .requestMatchers("/updateSubtaskStatus").authenticated()
                )
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
