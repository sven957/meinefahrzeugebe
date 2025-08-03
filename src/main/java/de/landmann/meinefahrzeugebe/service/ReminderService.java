package de.landmann.meinefahrzeugebe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import de.landmann.meinefahrzeugebe.entity.Reminder;
import de.landmann.meinefahrzeugebe.entity.ReminderStatus;
import de.landmann.meinefahrzeugebe.repository.ReminderRepository;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;

    public List<Reminder> getAllReminders() {
        return reminderRepository.findAll();
    }

    public List<Reminder> getAllReminders(Sort sort) {
        return reminderRepository.findAll(sort);
    }

    public Optional<Reminder> getReminderById(Long id) {
        return reminderRepository.findById(id);
    }

    public Reminder createReminder(Reminder reminder) {
        return reminderRepository.save(reminder);
    }

    public void deleteReminder(Long id) {
        reminderRepository.deleteById(id);
    }

    public List<Reminder> getRemindersByVehicleId(Long vehicleId) {
        return reminderRepository.findByVehicleId(vehicleId);
    }

    public Reminder updateReminderStatus(Long id, ReminderStatus status) {
        Optional<Reminder> reminderOpt = reminderRepository.findById(id);
        if (reminderOpt.isPresent()) {
            Reminder reminder = reminderOpt.get();
            reminder.setStatus(status);
            
            // Set completed date when marking as completed
            if (status == ReminderStatus.COMPLETED) {
                reminder.setCompletedDate(LocalDate.now());
            } else if (status == ReminderStatus.PENDING) {
                // Clear completed date when marking as pending again
                reminder.setCompletedDate(null);
            }
            
            return reminderRepository.save(reminder);
        }
        throw new RuntimeException("Reminder not found with id: " + id);
    }

    public Reminder markAsCompleted(Long id) {
        return updateReminderStatus(id, ReminderStatus.COMPLETED);
    }

    public Reminder markAsPending(Long id) {
        return updateReminderStatus(id, ReminderStatus.PENDING);
    }

    public Reminder updateReminder(Long id, Reminder updatedReminder) {
        Optional<Reminder> existingReminderOpt = reminderRepository.findById(id);
        if (existingReminderOpt.isPresent()) {
            Reminder existingReminder = existingReminderOpt.get();
            
            // Update fields
            existingReminder.setTitle(updatedReminder.getTitle());
            existingReminder.setDescription(updatedReminder.getDescription());
            existingReminder.setDueDate(updatedReminder.getDueDate());
            existingReminder.setType(updatedReminder.getType());
            existingReminder.setStatus(updatedReminder.getStatus());
            
            // Handle completed date based on status
            if (updatedReminder.getStatus() == ReminderStatus.COMPLETED && existingReminder.getCompletedDate() == null) {
                existingReminder.setCompletedDate(LocalDate.now());
            } else if (updatedReminder.getStatus() != ReminderStatus.COMPLETED) {
                existingReminder.setCompletedDate(null);
            }
            
            return reminderRepository.save(existingReminder);
        }
        throw new RuntimeException("Reminder not found with id: " + id);
    }
}
