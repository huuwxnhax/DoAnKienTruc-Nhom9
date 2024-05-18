package vn.edu.iuh.fit.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.userservice.auth.UserPrincipal;
import vn.edu.iuh.fit.userservice.entity.User;
import vn.edu.iuh.fit.userservice.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public User getUerById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User updateUser(Long id, User userNew) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userOld = optionalUser.get();
            if (userNew.getPassword() != null && !userNew.getPassword().isEmpty()) {
                userOld.setPassword(userNew.getPassword());
            }
            return userRepository.save(userOld);
        }
        return null;
    }


    @Override
    public String deleteUserById(Long id) {
        userRepository.deleteById(id);
        return "Delete user successfully!";
    }

    @Override
    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public UserPrincipal findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal();

        if (null != user) {

            Set<String> authorities = new HashSet<>();

            if (null != user.getRoles())

                user.getRoles().forEach(r -> {
                    authorities.add(r.getRoleKey());
                    r.getPermissions().forEach(
                            p -> authorities.add(p.getPermissionKey()));
                });

            userPrincipal.setUserId(user.getId());
            userPrincipal.setUsername(user.getUsername());
            userPrincipal.setPassword(user.getPassword());
            userPrincipal.setAuthorities(authorities);

        }

        return userPrincipal;

    }
}
