package com.example.MusicPlayer.Daos;

import com.example.MusicPlayer.Models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserDao {
    private JdbcTemplate jdbcTemplate;
    private PasswordEncoder passwordEncoder;
    private UserDao(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return jdbcTemplate.query(
                "SELECT * FROM users order by username", this::mapRowToUser);
    }
    public User getUser(String username) {
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM users WHERE username = ?",
                    this::mapRowToUser, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<String> getRolesForUser(String username) {
        return jdbcTemplate.queryForList("SELECT role FROM roles WHERE username = ?", String.class, username);
    }

    public void addRoleToUser(String username, String role){
        try {
            jdbcTemplate.update("INSERT INTO roles (username, role) VALUES (?, ?)", username, role);
        } catch (Exception e) {
        }
    }

    public void removeRoleFromUser(String username, String role) {
        jdbcTemplate.update("DELETE FROM roles WHERE username = ? AND role = ?", username, role);
    }

    public boolean checkUsernamePassword(String username, String password) {
        User user = getUser(username);
        return passwordEncoder.matches(password, user.getPassword());
    }

    public User createUser(User user){
        try {
            jdbcTemplate.update("INSERT INTO users (username, password, email) VALUES (?, ?, ?",
                    user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getEmail());
            return getUser(user.getUsername());
        } catch (Exception e) {
            return null;
        }
    }


    public User updateUser(User user, boolean updatePassword){
        if (updatePassword) {
            jdbcTemplate.update("UPDATE users SET password = ?, email = ? WHERE username = ?",
                    passwordEncoder.encode(user.getPassword()), user.getEmail(), user.getUsername());
        }else {
            jdbcTemplate.update("UPDATE users SET email = ? WHERE username = ?",
                    user.getEmail(), user.getUsername());

        }
        return getUser(user.getUsername());
    }

    public void deleteUser(String username) {
        jdbcTemplate.update("DELTE FROM users WHERE username = ?", username);
    }
    private User mapRowToUser(ResultSet row, int rowNum) throws SQLException {
        User user = new User();
        user.setUsername(row.getString("username"));
        user.setPassword(row.getString("password"));
        user.setEmail(row.getString("email"));
        return user;
    }
}
