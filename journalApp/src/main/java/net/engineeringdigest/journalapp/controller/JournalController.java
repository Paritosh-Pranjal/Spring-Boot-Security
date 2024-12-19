package net.engineeringdigest.journalapp.controller;

import net.engineeringdigest.journalapp.entity.Journal;
import net.engineeringdigest.journalapp.entity.User;
import net.engineeringdigest.journalapp.service.JournalService;
import net.engineeringdigest.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Journal>> getAllJournalOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<Journal> journals = user.getJournalEntries();
        if (journals != null && !journals.isEmpty()) {
            return new ResponseEntity<>(journals, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createEntity(@RequestBody Journal myEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        journalService.saveEntry(myEntry, userName);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Journal entry created successfully.");
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<Journal> getJournalById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<Journal> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<Journal> journal = journalService.findById(myId);
            if (journal.isPresent()) {
                return new ResponseEntity<>(journal.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<String> deleteJournalById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean deleted = journalService.deleteById(myId, userName);

        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)  // 204 No Content
                    .body("Journal entry deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)  // 404 Not Found
                    .body("Journal entry not found.");
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Journal> update(@PathVariable ObjectId id, @RequestBody Journal myEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Journal updatedJournal = journalService.updateJournal(id, myEntry, userName);

        if (updatedJournal != null) {
            return ResponseEntity.ok(updatedJournal);  // 200 OK with updated journal
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)  // 404 Not Found
                    .body(null);
        }
    }
}
