package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.ProductDto;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Produkty", description = "Zarządzanie produktami w magazynie")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(summary = "Pobierz wszystkie produkty", description = "Zwraca listę wszystkich produktów w magazynie")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz produkt po ID", description = "Zwraca szczegóły produktu o podanym ID")
    public ResponseEntity<ProductDto> getProductById(
            @Parameter(description = "ID produktu") @PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Pobierz produkt po SKU", description = "Zwraca szczegóły produktu o podanym SKU")
    public ResponseEntity<ProductDto> getProductBySku(
            @Parameter(description = "SKU produktu") @PathVariable String sku) {
        return productService.getProductBySku(sku)
                .map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "Wyszukaj produkty", description = "Wyszukuje produkty po nazwie")
    public ResponseEntity<List<ProductDto>> searchProducts(
            @Parameter(description = "Nazwa produktu do wyszukania") @RequestParam String name) {
        List<ProductDto> products = productService.searchProducts(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Produkty o niskim stanie", description = "Zwraca produkty o stanie poniżej minimalnego")
    public ResponseEntity<List<ProductDto>> getLowStockProducts() {
        List<ProductDto> products = productService.getLowStockProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    @Operation(summary = "Dodaj nowy produkt", description = "Tworzy nowy produkt w magazynie")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        try {
            ProductDto createdProduct = productService.createProduct(productDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aktualizuj produkt", description = "Aktualizuje dane produktu o podanym ID")
    public ResponseEntity<ProductDto> updateProduct(
            @Parameter(description = "ID produktu") @PathVariable Long id,
            @Valid @RequestBody ProductDto productDto) {
        try {
            ProductDto updatedProduct = productService.updateProduct(id, productDto);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń produkt", description = "Usuwa produkt o podanym ID")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID produktu") @PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
