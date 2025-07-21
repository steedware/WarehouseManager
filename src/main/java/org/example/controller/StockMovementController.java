package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.StockMovementDto;
import org.example.entity.MovementType;
import org.example.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
@Tag(name = "Ruchy magazynowe", description = "Zarządzanie ruchami magazynowymi")
public class StockMovementController {

    @Autowired
    private StockMovementService stockMovementService;

    @GetMapping
    @Operation(summary = "Pobierz wszystkie ruchy magazynowe", description = "Zwraca listę wszystkich ruchów magazynowych")
    public ResponseEntity<List<StockMovementDto>> getAllStockMovements() {
        List<StockMovementDto> movements = stockMovementService.getAllStockMovements();
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz ruch magazynowy po ID", description = "Zwraca szczegóły ruchu magazynowego o podanym ID")
    public ResponseEntity<StockMovementDto> getStockMovementById(
            @Parameter(description = "ID ruchu magazynowego") @PathVariable Long id) {
        return stockMovementService.getStockMovementById(id)
                .map(movement -> ResponseEntity.ok(movement))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Pobierz ruchy magazynowe produktu", description = "Zwraca wszystkie ruchy magazynowe dla danego produktu")
    public ResponseEntity<List<StockMovementDto>> getStockMovementsByProduct(
            @Parameter(description = "ID produktu") @PathVariable Long productId) {
        List<StockMovementDto> movements = stockMovementService.getStockMovementsByProduct(productId);
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/location/{locationId}")
    @Operation(summary = "Pobierz ruchy magazynowe lokalizacji", description = "Zwraca wszystkie ruchy magazynowe dla danej lokalizacji")
    public ResponseEntity<List<StockMovementDto>> getStockMovementsByLocation(
            @Parameter(description = "ID lokalizacji") @PathVariable Long locationId) {
        List<StockMovementDto> movements = stockMovementService.getStockMovementsByLocation(locationId);
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/type/{movementType}")
    @Operation(summary = "Pobierz ruchy magazynowe po typie", description = "Zwraca wszystkie ruchy magazynowe danego typu")
    public ResponseEntity<List<StockMovementDto>> getStockMovementsByType(
            @Parameter(description = "Typ ruchu magazynowego") @PathVariable MovementType movementType) {
        List<StockMovementDto> movements = stockMovementService.getStockMovementsByType(movementType);
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/date-range")
    @Operation(summary = "Pobierz ruchy magazynowe z okresu", description = "Zwraca ruchy magazynowe z określonego okresu czasowego")
    public ResponseEntity<List<StockMovementDto>> getStockMovementsByDateRange(
            @Parameter(description = "Data początkowa (format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Data końcowa (format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<StockMovementDto> movements = stockMovementService.getStockMovementsByDateRange(startDate, endDate);
        return ResponseEntity.ok(movements);
    }

    @PostMapping
    @Operation(summary = "Dodaj nowy ruch magazynowy", description = "Tworzy nowy ruch magazynowy i aktualizuje stan produktu")
    public ResponseEntity<StockMovementDto> createStockMovement(@Valid @RequestBody StockMovementDto stockMovementDto) {
        try {
            StockMovementDto createdMovement = stockMovementService.createStockMovement(stockMovementDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMovement);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
