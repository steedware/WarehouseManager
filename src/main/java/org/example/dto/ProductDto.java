package org.example.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductDto {
    private Long id;

    @NotBlank(message = "Nazwa produktu nie może być pusta")
    @Size(max = 255, message = "Nazwa produktu nie może być dłuższa niż 255 znaków")
    private String name;

    @Size(max = 1000, message = "Opis nie może być dłuższy niż 1000 znaków")
    private String description;

    @NotBlank(message = "SKU nie może być puste")
    @Size(max = 100, message = "SKU nie może być dłuższe niż 100 znaków")
    private String sku;

    @NotNull(message = "Cena nie może być pusta")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cena musi być większa od 0")
    private BigDecimal price;

    private Integer stock;

    @NotNull(message = "Minimalny stan nie może być pusty")
    private Integer minStock;

    private Long locationId;

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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getMinStock() {
        return minStock;
    }

    public void setMinStock(Integer minStock) {
        this.minStock = minStock;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}
