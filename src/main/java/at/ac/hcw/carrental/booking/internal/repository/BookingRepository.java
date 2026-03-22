package at.ac.hcw.carrental.booking.internal.repository;

import at.ac.hcw.carrental.booking.internal.model.BookingEntity;
import at.ac.hcw.carrental.car.internal.model.CarEntity;
import at.ac.hcw.carrental.user.internal.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {

    List<BookingEntity> findAllByUser(UserEntity user);

    @Query("""
        SELECT COUNT(b) > 0 FROM BookingEntity b
        WHERE b.car = :car
          AND b.status IN (
              at.ac.hcw.carrental.booking.internal.model.BookingStatus.PENDING,
              at.ac.hcw.carrental.booking.internal.model.BookingStatus.CONFIRMED
          )
          AND b.pickupDateTime < :returnDateTime
          AND b.returnDateTime > :pickupDateTime
    """)
    boolean existsOverlappingBooking(
            @Param("car") CarEntity car,
            @Param("pickupDateTime") LocalDateTime pickupDateTime,
            @Param("returnDateTime") LocalDateTime returnDateTime
    );
}
