package de.landmann.meinefahrzeugebe.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import de.landmann.meinefahrzeugebe.entity.Reminder;
import de.landmann.meinefahrzeugebe.entity.ReminderStatus;
import de.landmann.meinefahrzeugebe.entity.ReminderType;
import de.landmann.meinefahrzeugebe.entity.Vehicle;
import de.landmann.meinefahrzeugebe.repository.ReminderRepository;
import de.landmann.meinefahrzeugebe.repository.VehicleRepository;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    
    private final VehicleRepository vehicleRepository;
    private final ReminderRepository reminderRepository;
    
    @Override
    public void run(String... args) throws Exception {
        if (vehicleRepository.count() == 0 && reminderRepository.count() == 0) {
            loadTestData();
        }
    }
    
    private void loadTestData() {
        // Testfahrzeuge erstellen
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setLicensePlate("B-FP 1234");
        vehicle1.setBrand("BMW");
        vehicle1.setModel("320d");
        vehicle1.setYear(2022);
        vehicle1.setDriverName("Max Mustermann");
        vehicle1.setDriverEmail("max.mustermann@example.com");
        vehicle1.setLeaseEndDate(LocalDate.now().plusMonths(6));
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setLicensePlate("M-FP 5678");
        vehicle2.setBrand("Mercedes");
        vehicle2.setModel("C200");
        vehicle2.setYear(2023);
        vehicle2.setDriverName("Anna Schmidt");
        vehicle2.setDriverEmail("anna.schmidt@example.com");
        vehicle2.setLeaseEndDate(LocalDate.now().plusDays(15)); // Läuft bald ab!
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setLicensePlate("K-FP 9999");
        vehicle3.setBrand("Audi");
        vehicle3.setModel("A4");
        vehicle3.setYear(2021);
        // Kein Fahrer zugewiesen
        vehicle3.setLeaseEndDate(LocalDate.now().plusYears(1));
        
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setLicensePlate("H-FP 1111");
        vehicle4.setBrand("VW");
        vehicle4.setModel("Passat");
        vehicle4.setYear(2020);
        vehicle4.setDriverName("Thomas Weber");
        vehicle4.setDriverEmail("thomas.weber@example.com");
        vehicle4.setLeaseEndDate(LocalDate.now().plusMonths(3));
        
        // Alle speichern
        vehicleRepository.save(vehicle1);
        vehicleRepository.save(vehicle2);
        vehicleRepository.save(vehicle3);
        vehicleRepository.save(vehicle4);
        
        System.out.println("✅ Testdaten wurden geladen: " + vehicleRepository.count() + " Fahrzeuge");

        Reminder reminder1 = new Reminder();
        reminder1.setVehicle(vehicle1);
        reminder1.setTitle("Ölwechsel fällig");
        reminder1.setType(ReminderType.MAINTENANCE);
        reminder1.setStatus(ReminderStatus.PENDING);
        reminder1.setDescription("Ölwechsel fällig");
        reminder1.setDueDate(LocalDate.now().plusDays(30));

        Reminder reminder2 = new Reminder();
        reminder2.setVehicle(vehicle2);
        reminder2.setTitle("Reifenwechsel fällig");
        reminder2.setType(ReminderType.MAINTENANCE);
        reminder2.setStatus(ReminderStatus.COMPLETED);
        reminder2.setDescription("Reifenwechsel fällig");
        reminder2.setDueDate(LocalDate.now().plusDays(15));

        Reminder reminder3 = new Reminder();
        reminder3.setVehicle(vehicle3);
        reminder3.setTitle("Inspektion fällig");
        reminder3.setType(ReminderType.TUV);
        reminder3.setStatus(ReminderStatus.PENDING);
        reminder3.setDescription("Inspektion fällig");
        reminder3.setDueDate(LocalDate.now().plusMonths(1));

        Reminder reminder4 = new Reminder();
        reminder4.setVehicle(vehicle4);
        reminder4.setTitle("Ablauf der Leasingfrist");
        reminder4.setType(ReminderType.LEASE_END);
        reminder4.setStatus(ReminderStatus.OVERDUE);
        reminder4.setDescription("Ablauf der Leasingfrist");
        reminder4.setDueDate(LocalDate.now().plusMonths(-2));

        Reminder reminder5 = new Reminder();
        reminder5.setVehicle(vehicle4);
        reminder5.setTitle("TÜV fällig");
        reminder5.setType(ReminderType.TUV);
        reminder5.setStatus(ReminderStatus.PENDING);
        reminder5.setDescription("TÜV fällig");
        reminder5.setDueDate(LocalDate.now().plusMonths(1));

        reminderRepository.save(reminder1);
        reminderRepository.save(reminder2);
        reminderRepository.save(reminder3);
        reminderRepository.save(reminder4);
        reminderRepository.save(reminder5);

        System.out.println("✅ Testdaten wurden geladen: " + reminderRepository.count() + " Erinnerungen");
    }
}