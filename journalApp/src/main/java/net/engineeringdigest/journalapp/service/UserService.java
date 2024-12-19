package net.engineeringdigest.journalapp.service;

import net.engineeringdigest.journalapp.entity.User;
import net.engineeringdigest.journalapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;

    public void saveNewUser(User user) {
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        userRepository.save(user);
    }

    public void saveAdmin(User user) {
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER","ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(User user) {
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public boolean deleteByUserName(String Username) {
        User user = userRepository.deleteByUserName(Username);
        if (user != null) {
            userRepository.deleteByUserName(Username);
            return true;
        } else {
            return false;
        }
    }

    public User updateUser(String userName, User newEntry) {
        User existingUser = userRepository.findByUserName(userName);
        existingUser.setUserName(newEntry.getUserName());
        existingUser.setPassword(passwordEncoder.encode(newEntry.getPassword()));
        userRepository.save(existingUser);
        return existingUser;
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}
