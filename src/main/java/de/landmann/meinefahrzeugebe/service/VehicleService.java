package de.landmann.meinefahrzeugebe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import de.landmann.meinefahrzeugebe.entity.Vehicle;
import de.landmann.meinefahrzeugebe.repository.VehicleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {
    
    private final VehicleRepository vehicleRepository;
    
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
    
    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }
    
    public Optional<Vehicle> getVehicleByLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate);
    }
    
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
    
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
    
    public List<Vehicle> getVehiclesByDriver(String driverName) {
        return vehicleRepository.findByDriverName(driverName);
    }
    
    public List<Vehicle> getUnassignedVehicles() {
        return vehicleRepository.findUnassignedVehicles();
    }
    
    // Fahrzeuge mit ablaufendem Leasing in den nächsten X Tagen
    public List<Vehicle> getVehiclesWithLeaseEndingSoon(int days) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(days);
        return vehicleRepository.findVehiclesWithLeaseEndingBetween(today, endDate);
    }
}