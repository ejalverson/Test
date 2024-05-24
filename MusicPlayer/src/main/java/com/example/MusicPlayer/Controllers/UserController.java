package com.example.MusicPlayer.Controllers;

import com.example.MusicPlayer.Daos.UserDao;
import com.example.MusicPlayer.Models.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{username}")
    public User getUser(@PathVariable String username){
        return userDao.getUser(username);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("")
    public User createUser(@RequestBody User user) {
       return userDao.createUser(user);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{username}")
    public User updateUser(@PathVariable String username, @RequestBody User user){
       return userDao.updateUser(user, false);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) {
        userDao.deleteUser(username);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{username}/roles")
    public List<String> getUserRoles(@PathVariable String username) {
        return userDao.getRolesForUser(username);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{username}/roles")
    public void addUserRole(@PathVariable String username, @RequestBody String role) {
        userDao.addRoleToUser(username, role);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{username}/roles/{role}")
    public void removeUserRole(@PathVariable String username, @PathVariable String role) {
        userDao.removeRoleFromUser(username, role);
    }
}
