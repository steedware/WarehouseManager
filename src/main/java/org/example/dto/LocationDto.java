package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LocationDto {
    private Long id;

    @NotBlank(message = "Nazwa lokalizacji nie może być pusta")
    @Size(max = 100, message = "Nazwa lokalizacji nie może być dłuższa niż 100 znaków")
    private String name;

    @Size(max = 500, message = "Opis nie może być dłuższy niż 500 znaków")
    private String description;

    @NotBlank(message = "Kod lokalizacji nie może być pusty")
    @Size(max = 20, message = "Kod lokalizacji nie może być dłuższy niż 20 znaków")
    private String code;

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
}
