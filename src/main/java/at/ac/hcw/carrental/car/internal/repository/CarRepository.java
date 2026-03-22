package at.ac.hcw.carrental.car.internal.repository;

import at.ac.hcw.carrental.car.internal.model.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarRepository extends JpaRepository<CarEntity, UUID> {
    Optional<CarEntity> findByLicensePlate(String licensePlate);
    List<CarEntity> findAllByAvailableTrue();
    List<CarEntity> findAllByAvailableTrueAndLocationIgnoreCase(String location);
    boolean existsByLicensePlate(String licensePlate);
}
