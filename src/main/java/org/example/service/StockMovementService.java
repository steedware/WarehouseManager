package org.example.service;

import org.example.dto.StockMovementDto;
import org.example.entity.Location;
import org.example.entity.MovementType;
import org.example.entity.Product;
import org.example.entity.StockMovement;
import org.example.repository.LocationRepository;
import org.example.repository.ProductRepository;
import org.example.repository.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StockMovementService {

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ProductService productService;

    public List<StockMovementDto> getAllStockMovements() {
        return stockMovementRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<StockMovementDto> getStockMovementById(Long id) {
        return stockMovementRepository.findById(id)
                .map(this::convertToDto);
    }

    public List<StockMovementDto> getStockMovementsByProduct(Long productId) {
        return stockMovementRepository.findByProductIdOrderByCreatedAtDesc(productId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<StockMovementDto> getStockMovementsByLocation(Long locationId) {
        return stockMovementRepository.findByLocationIdOrderByCreatedAtDesc(locationId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<StockMovementDto> getStockMovementsByType(MovementType movementType) {
        return stockMovementRepository.findByMovementTypeOrderByCreatedAtDesc(movementType).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<StockMovementDto> getStockMovementsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return stockMovementRepository.findByDateRangeOrderByCreatedAtDesc(startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public StockMovementDto createStockMovement(StockMovementDto stockMovementDto) {
        Product product = productRepository.findById(stockMovementDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Produkt nie został znaleziony"));

        Location location = null;
        if (stockMovementDto.getLocationId() != null) {
            location = locationRepository.findById(stockMovementDto.getLocationId())
                    .orElseThrow(() -> new IllegalArgumentException("Lokalizacja nie została znaleziona"));
        }

        StockMovement stockMovement = convertToEntity(stockMovementDto);
        stockMovement.setProduct(product);
        stockMovement.setLocation(location);

        updateProductStock(product, stockMovementDto.getMovementType(), stockMovementDto.getQuantity());

        StockMovement saved = stockMovementRepository.save(stockMovement);
        return convertToDto(saved);
    }

    private void updateProductStock(Product product, MovementType movementType, Integer quantity) {
        Integer currentStock = product.getStock();
        Integer newStock;

        switch (movementType) {
            case IN:
            case RETURN:
                newStock = currentStock + quantity;
                break;
            case OUT:
                newStock = currentStock - quantity;
                if (newStock < 0) {
                    throw new IllegalArgumentException("Niewystarczający stan magazynowy");
                }
                break;
            case ADJUSTMENT:
                newStock = quantity;
                break;
            case TRANSFER:
                newStock = currentStock;
                break;
            default:
                throw new IllegalArgumentException("Nieznany typ ruchu magazynowego");
        }

        productService.updateStock(product.getId(), newStock);
    }

    private StockMovementDto convertToDto(StockMovement stockMovement) {
        StockMovementDto dto = new StockMovementDto();
        dto.setId(stockMovement.getId());
        dto.setProductId(stockMovement.getProduct().getId());
        dto.setProductName(stockMovement.getProduct().getName());
        dto.setProductSku(stockMovement.getProduct().getSku());

        if (stockMovement.getLocation() != null) {
            dto.setLocationId(stockMovement.getLocation().getId());
            dto.setLocationCode(stockMovement.getLocation().getCode());
        }

        dto.setMovementType(stockMovement.getMovementType());
        dto.setQuantity(stockMovement.getQuantity());
        dto.setNotes(stockMovement.getNotes());
        dto.setReference(stockMovement.getReference());
        dto.setCreatedAt(stockMovement.getCreatedAt());

        return dto;
    }

    private StockMovement convertToEntity(StockMovementDto dto) {
        StockMovement stockMovement = new StockMovement();
        stockMovement.setMovementType(dto.getMovementType());
        stockMovement.setQuantity(dto.getQuantity());
        stockMovement.setNotes(dto.getNotes());
        stockMovement.setReference(dto.getReference());
        return stockMovement;
    }
}
