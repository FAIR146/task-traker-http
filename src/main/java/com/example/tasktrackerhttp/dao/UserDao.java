package com.example.tasktrackerhttp.dao;

import com.example.tasktrackerhttp.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        return user;
    };

    public User findUserByLogin (String login) {
        String sql = "SELECT id,name,password FROM \"user\" WHERE name = ?";
        User user =  jdbcTemplate.queryForObject(sql,userRowMapper,login);
        return user;
    }
}
