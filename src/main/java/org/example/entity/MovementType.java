package org.example.entity;

public enum MovementType {
    IN("Przyjęcie"),
    OUT("Wydanie"),
    TRANSFER("Przeniesienie"),
    ADJUSTMENT("Korekta"),
    RETURN("Zwrot");

    private final String displayName;

    MovementType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
