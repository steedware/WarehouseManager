package org.example.service;

import org.example.dto.ProductDto;
import org.example.entity.Location;
import org.example.entity.Product;
import org.example.repository.LocationRepository;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LocationRepository locationRepository;

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<ProductDto> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDto);
    }

    public Optional<ProductDto> getProductBySku(String sku) {
        return productRepository.findBySku(sku)
                .map(this::convertToDto);
    }

    public List<ProductDto> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getLowStockProducts() {
        return productRepository.findLowStockProducts().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto createProduct(ProductDto productDto) {
        if (productRepository.existsBySku(productDto.getSku())) {
            throw new IllegalArgumentException("Produkt z tym SKU już istnieje");
        }

        Product product = convertToEntity(productDto);
        if (productDto.getLocationId() != null) {
            Location location = locationRepository.findById(productDto.getLocationId())
                    .orElseThrow(() -> new IllegalArgumentException("Lokalizacja nie została znaleziona"));
            product.setLocation(location);
        }

        Product saved = productRepository.save(product);
        return convertToDto(saved);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produkt nie został znaleziony"));

        if (!existingProduct.getSku().equals(productDto.getSku()) &&
            productRepository.existsBySku(productDto.getSku())) {
            throw new IllegalArgumentException("Produkt z tym SKU już istnieje");
        }

        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setSku(productDto.getSku());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setMinStock(productDto.getMinStock());

        if (productDto.getLocationId() != null) {
            Location location = locationRepository.findById(productDto.getLocationId())
                    .orElseThrow(() -> new IllegalArgumentException("Lokalizacja nie została znaleziona"));
            existingProduct.setLocation(location);
        }

        Product updated = productRepository.save(existingProduct);
        return convertToDto(updated);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Produkt nie został znaleziony");
        }
        productRepository.deleteById(id);
    }

    public void updateStock(Long productId, Integer newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produkt nie został znaleziony"));
        product.setStock(newStock);
        productRepository.save(product);
    }

    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setSku(product.getSku());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setMinStock(product.getMinStock());
        if (product.getLocation() != null) {
            dto.setLocationId(product.getLocation().getId());
        }
        return dto;
    }

    private Product convertToEntity(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setSku(dto.getSku());
        product.setPrice(dto.getPrice());
        product.setMinStock(dto.getMinStock());
        if (dto.getStock() != null) {
            product.setStock(dto.getStock());
        }
        return product;
    }
}
