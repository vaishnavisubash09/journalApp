package com.engineeringdigest.journalApp.controller;

import com.engineeringdigest.journalApp.entity.JournalEntry;
import com.engineeringdigest.journalApp.entity.User;
import com.engineeringdigest.journalApp.service.JournalEntryService;
import com.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/{username}") // basically this method gives the journal entries of a specific user only
    public ResponseEntity<?> getJournalsByUsername(@PathVariable String username) {
        User user = userService.getUser(username);
        List<JournalEntry> getAll = user.getUserJournalEntries();
        if (getAll != null && !getAll.isEmpty()) {
            return new ResponseEntity<>(getAll, HttpStatus.OK);
        }

        return new ResponseEntity<>(getAll, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntryThroughPostman, @PathVariable String username){

        try {

            journalEntryService.saveEntry(myEntryThroughPostman,username);
            return new ResponseEntity<>(myEntryThroughPostman,HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> getJournalEntryByID(@PathVariable ObjectId myid){
        Optional<JournalEntry> journalEntry = journalEntryService.getJournalEntryById(myid);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{username}/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId id, @PathVariable String username){
        journalEntryService.deleteJournalEntryById(id, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{username}/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry, @PathVariable String username){
        JournalEntry oldEntry = journalEntryService.getJournalEntryById(id).orElse(null);
        if(oldEntry != null) {
            oldEntry.setTitle((newEntry.getTitle() != null && !newEntry.getTitle().equals("")) ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent((newEntry.getContent() != null && !newEntry.getContent().equals("")) ? newEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}
