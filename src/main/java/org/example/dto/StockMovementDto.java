package org.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.entity.MovementType;

import java.time.LocalDateTime;

public class StockMovementDto {
    private Long id;

    @NotNull(message = "ID produktu nie może być puste")
    private Long productId;

    private Long locationId;

    @NotNull(message = "Typ ruchu nie może być pusty")
    private MovementType movementType;

    @NotNull(message = "Ilość nie może być pusta")
    private Integer quantity;

    @Size(max = 500, message = "Uwagi nie mogą być dłuższe niż 500 znaków")
    private String notes;

    @Size(max = 100, message = "Referencja nie może być dłuższa niż 100 znaków")
    private String reference;

    private LocalDateTime createdAt;

    private String productName;
    private String productSku;
    private String locationCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
}
