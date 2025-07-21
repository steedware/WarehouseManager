package org.example.service;

import com.opencsv.CSVWriter;
import org.example.dto.ProductDto;
import org.example.dto.StockMovementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ProductService productService;

    @Autowired
    private StockMovementService stockMovementService;

    public String generateProductsReport() {
        List<ProductDto> products = productService.getAllProducts();

        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);

        String[] header = {"ID", "Nazwa", "SKU", "Cena", "Stan", "Min. stan", "ID Lokalizacji"};
        csvWriter.writeNext(header);

        for (ProductDto product : products) {
            String[] row = {
                product.getId() != null ? product.getId().toString() : "",
                product.getName() != null ? product.getName() : "",
                product.getSku() != null ? product.getSku() : "",
                product.getPrice() != null ? product.getPrice().toString() : "",
                product.getStock() != null ? product.getStock().toString() : "",
                product.getMinStock() != null ? product.getMinStock().toString() : "",
                product.getLocationId() != null ? product.getLocationId().toString() : ""
            };
            csvWriter.writeNext(row);
        }

        try {
            csvWriter.close();
        } catch (Exception e) {
            throw new RuntimeException("Błąd podczas generowania raportu produktów", e);
        }

        return stringWriter.toString();
    }

    public String generateLowStockReport() {
        List<ProductDto> lowStockProducts = productService.getLowStockProducts();

        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);

        String[] header = {"ID", "Nazwa", "SKU", "Aktualny stan", "Min. stan", "Różnica"};
        csvWriter.writeNext(header);

        for (ProductDto product : lowStockProducts) {
            String[] row = {
                product.getId() != null ? product.getId().toString() : "",
                product.getName() != null ? product.getName() : "",
                product.getSku() != null ? product.getSku() : "",
                product.getStock() != null ? product.getStock().toString() : "",
                product.getMinStock() != null ? product.getMinStock().toString() : "",
                (product.getStock() != null && product.getMinStock() != null) ?
                    String.valueOf(product.getMinStock() - product.getStock()) : ""
            };
            csvWriter.writeNext(row);
        }

        try {
            csvWriter.close();
        } catch (Exception e) {
            throw new RuntimeException("Błąd podczas generowania raportu niskich stanów", e);
        }

        return stringWriter.toString();
    }

    public String generateStockMovementsReport(LocalDateTime startDate, LocalDateTime endDate) {
        List<StockMovementDto> movements;

        if (startDate != null && endDate != null) {
            movements = stockMovementService.getStockMovementsByDateRange(startDate, endDate);
        } else {
            movements = stockMovementService.getAllStockMovements();
        }

        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);

        String[] header = {"ID", "Produkt", "SKU", "Typ ruchu", "Ilość", "Lokalizacja", "Data", "Uwagi", "Referencja"};
        csvWriter.writeNext(header);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (StockMovementDto movement : movements) {
            String[] row = {
                movement.getId() != null ? movement.getId().toString() : "",
                movement.getProductName() != null ? movement.getProductName() : "",
                movement.getProductSku() != null ? movement.getProductSku() : "",
                movement.getMovementType() != null ? movement.getMovementType().getDisplayName() : "",
                movement.getQuantity() != null ? movement.getQuantity().toString() : "",
                movement.getLocationCode() != null ? movement.getLocationCode() : "",
                movement.getCreatedAt() != null ? movement.getCreatedAt().format(formatter) : "",
                movement.getNotes() != null ? movement.getNotes() : "",
                movement.getReference() != null ? movement.getReference() : ""
            };
            csvWriter.writeNext(row);
        }

        try {
            csvWriter.close();
        } catch (Exception e) {
            throw new RuntimeException("Błąd podczas generowania raportu ruchów magazynowych", e);
        }

        return stringWriter.toString();
    }
}
