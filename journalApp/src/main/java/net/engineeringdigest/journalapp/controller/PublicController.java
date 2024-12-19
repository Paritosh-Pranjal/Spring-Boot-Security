package net.engineeringdigest.journalapp.controller;

import net.engineeringdigest.journalapp.entity.User;
import net.engineeringdigest.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<String> createEntity(@RequestBody User myEntry) {
        userService.saveNewUser(myEntry);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User entry created successfully.");
    }
}
