-- Przykładowe dane dla systemu zarządzania magazynem

-- Lokalizacje
INSERT INTO locations (name, description, code, created_at) VALUES
('Magazyn główny - Sektor A', 'Główny sektor magazynowy dla elektroniki', 'MAG-A', NOW()),
('Magazyn główny - Sektor B', 'Sektor magazynowy dla akcesoriów', 'MAG-B', NOW()),
('Strefa odbioru', 'Obszar do odbioru nowych dostaw', 'RECV', NOW()),
('Strefa wysyłki', 'Obszar do przygotowania wysyłek', 'SHIP', NOW());

-- Produkty (będą dodane z ID lokalizacji po ich utworzeniu)
INSERT INTO products (name, description, sku, price, stock, min_stock, created_at, updated_at) VALUES
('Laptop Dell XPS 13', 'Ultrabook do pracy biurowej z procesorem Intel i5', 'DELL-XPS13-001', 4999.99, 15, 5, NOW(), NOW()),
('iPhone 15 Pro', 'Najnowszy smartfon Apple z chipem A17 Pro', 'APPLE-IP15P-001', 5499.00, 8, 3, NOW(), NOW()),
('Monitor Samsung 27"', 'Monitor 4K o przekątnej 27 cali', 'SAMS-MON27-001', 1299.99, 25, 10, NOW(), NOW()),
('Klawiatura mechaniczna', 'Klawiatura gamingowa z przełącznikami Cherry MX', 'KEYB-MECH-001', 299.99, 50, 15, NOW(), NOW()),
('Mysz bezprzewodowa', 'Ergonomiczna mysz z czujnikiem optycznym', 'MOUSE-WIRELESS-001', 89.99, 75, 20, NOW(), NOW());

-- Przykładowe ruchy magazynowe (będą dodane po utworzeniu produktów)
-- Te dane należy dodać po uruchomieniu aplikacji przez API
