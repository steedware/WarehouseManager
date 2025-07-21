package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nazwa lokalizacji nie może być pusta")
    @Size(max = 100, message = "Nazwa lokalizacji nie może być dłuższa niż 100 znaków")
    @Column(nullable = false)
    private String name;

    @Size(max = 500, message = "Opis nie może być dłuższy niż 500 znaków")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Kod lokalizacji nie może być pusty")
    @Size(max = 20, message = "Kod lokalizacji nie może być dłuższy niż 20 znaków")
    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Product> products;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<StockMovement> stockMovements;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<StockMovement> getStockMovements() {
        return stockMovements;
    }

    public void setStockMovements(List<StockMovement> stockMovements) {
        this.stockMovements = stockMovements;
    }
}
