package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.LocationDto;
import org.example.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@Tag(name = "Lokalizacje", description = "Zarządzanie lokalizacjami w magazynie")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    @Operation(summary = "Pobierz wszystkie lokalizacje", description = "Zwraca listę wszystkich lokalizacji w magazynie")
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        List<LocationDto> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz lokalizację po ID", description = "Zwraca szczegóły lokalizacji o podanym ID")
    public ResponseEntity<LocationDto> getLocationById(
            @Parameter(description = "ID lokalizacji") @PathVariable Long id) {
        return locationService.getLocationById(id)
                .map(location -> ResponseEntity.ok(location))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Pobierz lokalizację po kodzie", description = "Zwraca szczegóły lokalizacji o podanym kodzie")
    public ResponseEntity<LocationDto> getLocationByCode(
            @Parameter(description = "Kod lokalizacji") @PathVariable String code) {
        return locationService.getLocationByCode(code)
                .map(location -> ResponseEntity.ok(location))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "Wyszukaj lokalizacje", description = "Wyszukuje lokalizacje po nazwie")
    public ResponseEntity<List<LocationDto>> searchLocations(
            @Parameter(description = "Nazwa lokalizacji do wyszukania") @RequestParam String name) {
        List<LocationDto> locations = locationService.searchLocations(name);
        return ResponseEntity.ok(locations);
    }

    @PostMapping
    @Operation(summary = "Dodaj nową lokalizację", description = "Tworzy nową lokalizację w magazynie")
    public ResponseEntity<LocationDto> createLocation(@Valid @RequestBody LocationDto locationDto) {
        try {
            LocationDto createdLocation = locationService.createLocation(locationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLocation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aktualizuj lokalizację", description = "Aktualizuje dane lokalizacji o podanym ID")
    public ResponseEntity<LocationDto> updateLocation(
            @Parameter(description = "ID lokalizacji") @PathVariable Long id,
            @Valid @RequestBody LocationDto locationDto) {
        try {
            LocationDto updatedLocation = locationService.updateLocation(id, locationDto);
            return ResponseEntity.ok(updatedLocation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń lokalizację", description = "Usuwa lokalizację o podanym ID")
    public ResponseEntity<Void> deleteLocation(
            @Parameter(description = "ID lokalizacji") @PathVariable Long id) {
        try {
            locationService.deleteLocation(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
