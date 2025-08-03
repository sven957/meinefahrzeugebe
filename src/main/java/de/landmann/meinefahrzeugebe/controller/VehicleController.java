package de.landmann.meinefahrzeugebe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import de.landmann.meinefahrzeugebe.entity.Vehicle;
import de.landmann.meinefahrzeugebe.service.VehicleService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Für React Frontend
public class VehicleController {
    
    private final VehicleService vehicleService;
    
    @GetMapping
    public List<Vehicle> getAllVehicles(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = Sort.by(
            sortDir.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, 
            sortBy
        );
        return vehicleService.getAllVehicles(sort);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Vehicle createVehicle(@Valid @RequestBody Vehicle vehicle) {
        return vehicleService.saveVehicle(vehicle);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @Valid @RequestBody Vehicle vehicle) {
        return vehicleService.getVehicleById(id)
                .map(existingVehicle -> {
                    vehicle.setId(id);
                    return ResponseEntity.ok(vehicleService.saveVehicle(vehicle));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        if (vehicleService.getVehicleById(id).isPresent()) {
            vehicleService.deleteVehicle(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/lease-ending-soon")
    public List<Vehicle> getVehiclesWithLeaseEndingSoon(@RequestParam(defaultValue = "30") int days) {
        return vehicleService.getVehiclesWithLeaseEndingSoon(days);
    }
    
    @GetMapping("/unassigned")
    public List<Vehicle> getUnassignedVehicles() {
        return vehicleService.getUnassignedVehicles();
    }
}