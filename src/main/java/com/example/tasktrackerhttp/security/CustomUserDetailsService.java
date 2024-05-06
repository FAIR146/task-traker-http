package com.example.tasktrackerhttp.security;

import com.example.tasktrackerhttp.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.tasktrackerhttp.dto.User user = userDao.findUserByLogin(username);
        return User.builder()
                .username(user.getName())
                .password(user.getPassword())
                .roles()
                .build();
    }
}
