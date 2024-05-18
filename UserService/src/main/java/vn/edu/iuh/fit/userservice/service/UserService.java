package vn.edu.iuh.fit.userservice.service;

import vn.edu.iuh.fit.userservice.auth.UserPrincipal;
import vn.edu.iuh.fit.userservice.entity.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public User getUerById(Long id);
    public User updateUser(Long id, User user);
    public String deleteUserById(Long id);
    User createUser(User user);
    UserPrincipal findByUsername(String username);
}
