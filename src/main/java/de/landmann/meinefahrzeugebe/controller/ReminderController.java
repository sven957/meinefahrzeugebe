package de.landmann.meinefahrzeugebe.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.landmann.meinefahrzeugebe.entity.Reminder;
import de.landmann.meinefahrzeugebe.entity.ReminderStatus;
import de.landmann.meinefahrzeugebe.service.ReminderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/reminders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Für React Frontend
public class ReminderController {
    private final ReminderService reminderService;

    @GetMapping
    public List<Reminder> getAllReminders() {
        return reminderService.getAllReminders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reminder> getReminderById(@PathVariable Long id) {
        return reminderService.getReminderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reminder createReminder(@Valid @RequestBody Reminder reminder) {
        return reminderService.createReminder(reminder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long id) {
        if (reminderService.getReminderById(id).isPresent()) {
            reminderService.deleteReminder(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/vehicle/{vehicleId}")
    public List<Reminder> getRemindersByVehicleId(@PathVariable Long vehicleId) {
        return reminderService.getRemindersByVehicleId(vehicleId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reminder> updateReminder(@PathVariable Long id, @Valid @RequestBody Reminder reminder) {
        try {
            Reminder updatedReminder = reminderService.updateReminder(id, reminder);
            return ResponseEntity.ok(updatedReminder);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Reminder> updateReminderStatus(@PathVariable Long id, @RequestBody ReminderStatus status) {
        try {
            Reminder updatedReminder = reminderService.updateReminderStatus(id, status);
            return ResponseEntity.ok(updatedReminder);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Reminder> markReminderAsCompleted(@PathVariable Long id) {
        try {
            Reminder updatedReminder = reminderService.markAsCompleted(id);
            return ResponseEntity.ok(updatedReminder);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/pending")
    public ResponseEntity<Reminder> markReminderAsPending(@PathVariable Long id) {
        try {
            Reminder updatedReminder = reminderService.markAsPending(id);
            return ResponseEntity.ok(updatedReminder);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
