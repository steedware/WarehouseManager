package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Raporty", description = "Generowanie raportów magazynowych")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/products")
    @Operation(summary = "Raport wszystkich produktów", description = "Generuje raport CSV ze wszystkimi produktami")
    public ResponseEntity<String> generateProductsReport() {
        String csvContent = reportService.generateProductsReport();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv; charset=UTF-8"));
        headers.setContentDispositionFormData("attachment", "produkty_raport.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvContent);
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Raport niskich stanów", description = "Generuje raport CSV produktów o niskim stanie")
    public ResponseEntity<String> generateLowStockReport() {
        String csvContent = reportService.generateLowStockReport();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv; charset=UTF-8"));
        headers.setContentDispositionFormData("attachment", "niskie_stany_raport.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvContent);
    }

    @GetMapping("/stock-movements")
    @Operation(summary = "Raport ruchów magazynowych", description = "Generuje raport CSV ruchów magazynowych")
    public ResponseEntity<String> generateStockMovementsReport(
            @Parameter(description = "Data początkowa (opcjonalna, format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Data końcowa (opcjonalna, format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        String csvContent = reportService.generateStockMovementsReport(startDate, endDate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv; charset=UTF-8"));
        headers.setContentDispositionFormData("attachment", "ruchy_magazynowe_raport.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvContent);
    }
}
