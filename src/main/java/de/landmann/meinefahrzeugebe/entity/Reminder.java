package de.landmann.meinefahrzeugebe.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reminders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Vehicle vehicle;

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private ReminderType type;

    @Enumerated(EnumType.STRING)
    private ReminderStatus status = ReminderStatus.PENDING;

    private LocalDate completedDate;

    // Konstruktor ohne ID für neue Reminder
    public Reminder(Vehicle vehicle, String title, String description, LocalDate dueDate, ReminderType type) {
        this.vehicle = vehicle;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.type = type;
        this.status = ReminderStatus.PENDING;
    }
}
