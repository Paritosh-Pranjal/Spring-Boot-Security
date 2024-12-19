package net.engineeringdigest.journalapp.controller;

import net.engineeringdigest.journalapp.entity.User;
import net.engineeringdigest.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/{userName}")
    public ResponseEntity<User> getByUsername(@PathVariable String userName) {
        User users = userService.findByUserName(userName);

        if (users != null) {
            return ResponseEntity.ok(users);  // 200 OK with the users
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)  // 404 Not Found
                    .body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean deleted = userService.deleteByUserName(userName);

        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)  // 204 No Content
                    .body("User entry deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)  // 404 Not Found
                    .body("User entry not found.");
        }
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User myEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User updatedUser = userService.updateUser(userName, myEntry);

        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);  // 200 OK with updated users
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)  // 404 Not Found
                    .body(null);
        }
    }
}


//adding authentication to journal endpoints need to start