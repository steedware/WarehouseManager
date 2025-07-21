package org.example.repository;

import org.example.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByCode(String code);

    List<Location> findByNameContainingIgnoreCase(String name);

    boolean existsByCode(String code);
}
