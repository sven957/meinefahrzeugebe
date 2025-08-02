package de.landmann.meinefahrzeugebe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.landmann.meinefahrzeugebe.entity.Vehicle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
    Optional<Vehicle> findByLicensePlate(String licensePlate);
    
    List<Vehicle> findByDriverName(String driverName);
    
    List<Vehicle> findByBrand(String brand);
    
    // Fahrzeuge mit ablaufendem Leasing finden
    @Query("SELECT v FROM Vehicle v WHERE v.leaseEndDate BETWEEN :startDate AND :endDate")
    List<Vehicle> findVehiclesWithLeaseEndingBetween(LocalDate startDate, LocalDate endDate);
    
    // Fahrzeuge ohne zugewiesenen Fahrer
    @Query("SELECT v FROM Vehicle v WHERE v.driverName IS NULL OR v.driverName = ''")
    List<Vehicle> findUnassignedVehicles();
}