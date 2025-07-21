# System Zarządzania Magazynem

## Opis

Kompleksowy system zarządzania magazynem napisany w Java z użyciem Spring Boot. Aplikacja umożliwia pełne zarządzanie produktami, lokalizacjami magazynowymi, śledzenie ruchów magazynowych oraz generowanie raportów.

## Tech Stack

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Swagger/OpenAPI**
- **Docker & Docker Compose**
- **Maven**

## Funkcjonalności

### ✅ Zarządzanie Produktami
- Dodawanie, edycja, usuwanie produktów
- Wyszukiwanie po nazwie i SKU
- Kontrola stanów magazynowych
- Alerty o niskich stanach

### ✅ Lokalizacje Magazynowe
- Zarządzanie lokalizacjami w magazynie
- Przypisywanie produktów do lokalizacji
- Wyszukiwanie po kodach lokalizacji

### ✅ Historia Ruchów Magazynowych
- Śledzenie wszystkich operacji magazynowych
- Typy ruchów: Przyjęcie, Wydanie, Przeniesienie, Korekta, Zwrot
- Automatyczna aktualizacja stanów
- Filtrowanie po dacie, produkcie, lokalizacji

### ✅ System Raportów
- Eksport do CSV
- Raport wszystkich produktów
- Raport produktów o niskim stanie
- Raport ruchów magazynowych z filtrowaniem

### ✅ API Documentation
- Kompletna dokumentacja Swagger/OpenAPI
- Dostępna pod adresem: `http://localhost:8080/swagger-ui.html`

## Uruchamianie

### Opcja 1: Docker Compose (Zalecana)

```bash
# Klonowanie repozytorium
git clone https://github.com/steedware/WarehouseManager.git
cd WarehouseManager

# Budowanie aplikacji
mvn clean package -DskipTests

# Uruchomienie z Docker Compose
docker-compose up -d
```

### Opcja 2: Lokalne uruchomienie

#### Wymagania
- Java 21+
- Maven 3.6+
- PostgreSQL 12+

#### Konfiguracja bazy danych
```sql
CREATE DATABASE warehouse_db;
CREATE USER warehouse_user WITH PASSWORD 'warehouse_pass';
GRANT ALL PRIVILEGES ON DATABASE warehouse_db TO warehouse_user;
```

#### Uruchomienie
```bash
# Klonowanie repozytorium
git clone https://github.com/steedware/WarehouseManager.git
cd WarehouseManager

# Instalacja zależności i uruchomienie
mvn clean install
mvn spring-boot:run
```

## Endpoints API

### Produkty
- `GET /api/products` - Lista wszystkich produktów
- `POST /api/products` - Dodanie nowego produktu
- `GET /api/products/{id}` - Szczegóły produktu
- `PUT /api/products/{id}` - Aktualizacja produktu
- `DELETE /api/products/{id}` - Usunięcie produktu
- `GET /api/products/low-stock` - Produkty o niskim stanie

### Lokalizacje
- `GET /api/locations` - Lista wszystkich lokalizacji
- `POST /api/locations` - Dodanie nowej lokalizacji
- `GET /api/locations/{id}` - Szczegóły lokalizacji
- `PUT /api/locations/{id}` - Aktualizacja lokalizacji
- `DELETE /api/locations/{id}` - Usunięcie lokalizacji

### Ruchy Magazynowe
- `GET /api/stock-movements` - Lista wszystkich ruchów
- `POST /api/stock-movements` - Dodanie nowego ruchu
- `GET /api/stock-movements/product/{id}` - Ruchy dla produktu
- `GET /api/stock-movements/date-range` - Ruchy z okresu

### Raporty
- `GET /api/reports/products` - Raport produktów (CSV)
- `GET /api/reports/low-stock` - Raport niskich stanów (CSV)
- `GET /api/reports/stock-movements` - Raport ruchów (CSV)

## Struktura Bazy Danych

### Tabela products
- `id` (PK) - Identyfikator produktu
- `name` - Nazwa produktu
- `description` - Opis produktu
- `sku` - Kod SKU (unikalny)
- `price` - Cena produktu
- `stock` - Aktualny stan
- `min_stock` - Minimalny stan
- `location_id` (FK) - Powiązanie z lokalizacją
- `created_at`, `updated_at` - Znaczniki czasu

### Tabela locations
- `id` (PK) - Identyfikator lokalizacji
- `name` - Nazwa lokalizacji
- `description` - Opis lokalizacji
- `code` - Kod lokalizacji (unikalny)
- `created_at` - Data utworzenia

### Tabela stock_movements
- `id` (PK) - Identyfikator ruchu
- `product_id` (FK) - Powiązanie z produktem
- `location_id` (FK) - Powiązanie z lokalizacją
- `movement_type` - Typ ruchu (ENUM)
- `quantity` - Ilość
- `notes` - Uwagi
- `reference` - Numer referencyjny
- `created_at` - Data utworzenia

## Przykładowe Żądania

### Dodanie produktu
```json
POST /api/products
{
    "name": "Laptop Dell XPS 13",
    "description": "Ultrabook do pracy biurowej",
    "sku": "DELL-XPS13-001",
    "price": 4999.99,
    "minStock": 5,
    "locationId": 1
}
```

### Dodanie ruchu magazynowego
```json
POST /api/stock-movements
{
    "productId": 1,
    "locationId": 1,
    "movementType": "IN",
    "quantity": 10,
    "notes": "Dostawa od dostawcy",
    "reference": "DOC-2024-001"
}
```

## Technologie i Wzorce

- **Architektura warstwowa** (Controller → Service → Repository)
- **DTO Pattern** dla separacji warstw
- **Repository Pattern** z Spring Data JPA
- **Bean Validation** dla walidacji danych
- **Transakcje** dla spójności danych
- **OpenAPI 3.0** dla dokumentacji API

## Autor

Stworzono przez [steedware](https://github.com/steedware)

## Licencja

MIT License
