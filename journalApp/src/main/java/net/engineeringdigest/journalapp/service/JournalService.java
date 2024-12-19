package net.engineeringdigest.journalapp.service;

import net.engineeringdigest.journalapp.entity.Journal;
import net.engineeringdigest.journalapp.entity.User;
import net.engineeringdigest.journalapp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(Journal journal, String userName) {
        try {
            User user = userService.findByUserName(userName);
            journal.setDate(LocalDateTime.now());
            Journal saved = journalRepository.save(journal);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.print(e);
            throw new RuntimeException("An error occured while saving the entry.", e);
        }

    }

    public void saveEntry(Journal journal) {
        journalRepository.save(journal);
    }

    public List<Journal> getAll(String userName) {
        return journalRepository.findAll();
    }

    public Optional<Journal> findById(ObjectId id) {
        return journalRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        try {
            User user = userService.findByUserName(userName);
            boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                Optional<Journal> journal = journalRepository.findById(id);
                if (journal.isPresent()) {
                    journalRepository.deleteById(id);
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while delete the journal ", e);
        }
    }


    public Journal updateJournal(ObjectId id, Journal newEntry, String userName) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!user.getJournalEntries().stream().anyMatch(journal -> journal.getId().equals(id))) {
            throw new RuntimeException("User does not own this journal entry");
        }

        Journal existingJournal = journalRepository.findById(id).orElse(null);
        if (existingJournal != null) {
            existingJournal.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : existingJournal.getTitle());
            existingJournal.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : existingJournal.getContent());
        }
        journalRepository.save(existingJournal);
        return existingJournal;
    }
}
