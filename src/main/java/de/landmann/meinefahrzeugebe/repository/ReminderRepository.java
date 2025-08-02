package de.landmann.meinefahrzeugebe.repository;

import de.landmann.meinefahrzeugebe.entity.Reminder;
import de.landmann.meinefahrzeugebe.entity.ReminderStatus;
import de.landmann.meinefahrzeugebe.entity.ReminderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    
    List<Reminder> findByVehicleId(Long vehicleId);
    
    List<Reminder> findByStatus(ReminderStatus status);
    
    List<Reminder> findByType(ReminderType type);
    
    // Reminder die in den nächsten X Tagen fällig sind
    @Query("SELECT r FROM Reminder r WHERE r.dueDate BETWEEN :startDate AND :endDate AND r.status = :status")
    List<Reminder> findUpcomingReminders(LocalDate startDate, LocalDate endDate, ReminderStatus status);
    
    // Überfällige Reminder
    @Query("SELECT r FROM Reminder r WHERE r.dueDate < :today AND r.status = 'PENDING'")
    List<Reminder> findOverdueReminders(LocalDate today);
    
    // Dashboard Statistiken
    @Query("SELECT COUNT(r) FROM Reminder r WHERE r.status = 'PENDING'")
    long countPendingReminders();
    
    @Query("SELECT COUNT(r) FROM Reminder r WHERE r.dueDate BETWEEN :startDate AND :endDate AND r.status = 'PENDING'")
    long countUpcomingReminders(LocalDate startDate, LocalDate endDate);
}