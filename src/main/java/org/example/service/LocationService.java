package org.example.service;

import org.example.dto.LocationDto;
import org.example.entity.Location;
import org.example.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<LocationDto> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<LocationDto> getLocationById(Long id) {
        return locationRepository.findById(id)
                .map(this::convertToDto);
    }

    public Optional<LocationDto> getLocationByCode(String code) {
        return locationRepository.findByCode(code)
                .map(this::convertToDto);
    }

    public List<LocationDto> searchLocations(String name) {
        return locationRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public LocationDto createLocation(LocationDto locationDto) {
        if (locationRepository.existsByCode(locationDto.getCode())) {
            throw new IllegalArgumentException("Lokalizacja z tym kodem już istnieje");
        }

        Location location = convertToEntity(locationDto);
        Location saved = locationRepository.save(location);
        return convertToDto(saved);
    }

    public LocationDto updateLocation(Long id, LocationDto locationDto) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lokalizacja nie została znaleziona"));

        if (!existingLocation.getCode().equals(locationDto.getCode()) &&
            locationRepository.existsByCode(locationDto.getCode())) {
            throw new IllegalArgumentException("Lokalizacja z tym kodem już istnieje");
        }

        existingLocation.setName(locationDto.getName());
        existingLocation.setDescription(locationDto.getDescription());
        existingLocation.setCode(locationDto.getCode());

        Location updated = locationRepository.save(existingLocation);
        return convertToDto(updated);
    }

    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new IllegalArgumentException("Lokalizacja nie została znaleziona");
        }
        locationRepository.deleteById(id);
    }

    private LocationDto convertToDto(Location location) {
        LocationDto dto = new LocationDto();
        dto.setId(location.getId());
        dto.setName(location.getName());
        dto.setDescription(location.getDescription());
        dto.setCode(location.getCode());
        return dto;
    }

    private Location convertToEntity(LocationDto dto) {
        Location location = new Location();
        location.setName(dto.getName());
        location.setDescription(dto.getDescription());
        location.setCode(dto.getCode());
        return location;
    }
}
