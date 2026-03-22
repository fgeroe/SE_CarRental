package at.ac.hcw.carrental.booking;

import at.ac.hcw.carrental.booking.dto.BookingResponse;
import at.ac.hcw.carrental.booking.dto.CreateBookingRequest;
import at.ac.hcw.carrental.booking.internal.mapper.BookingMapper;
import at.ac.hcw.carrental.booking.internal.model.BookingEntity;
import at.ac.hcw.carrental.booking.internal.model.BookingStatus;
import at.ac.hcw.carrental.booking.internal.repository.BookingRepository;
import at.ac.hcw.carrental.car.internal.model.CarEntity;
import at.ac.hcw.carrental.car.internal.repository.CarRepository;
import at.ac.hcw.carrental.user.internal.model.UserEntity;
import at.ac.hcw.carrental.user.internal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    public BookingResponse createBooking(CreateBookingRequest request) {
        UserEntity user = getCurrentUser();

        CarEntity car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        if (!request.getReturnDateTime().isAfter(request.getPickupDateTime())) {
            throw new IllegalArgumentException("Return date must be after pickup date");
        }

        if (bookingRepository.existsOverlappingBooking(car, request.getPickupDateTime(), request.getReturnDateTime())) {
            throw new IllegalStateException("Car is not available for the selected dates");
        }

        long hours = ChronoUnit.HOURS.between(request.getPickupDateTime(), request.getReturnDateTime());
        long days = Math.max(1, (long) Math.ceil(hours / 24.0));
        BigDecimal totalPrice = car.getDailyRate().multiply(BigDecimal.valueOf(days));

        BookingEntity booking = BookingEntity.builder()
                .user(user)
                .car(car)
                .pickupDateTime(request.getPickupDateTime())
                .returnDateTime(request.getReturnDateTime())
                .totalPrice(totalPrice)
                .build();

        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getMyBookings() {
        UserEntity user = getCurrentUser();
        return bookingRepository.findAllByUser(user).stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookingResponse getBookingById(UUID id) {
        BookingEntity booking = findOrThrow(id);
        assertOwnerOrAdmin(booking);
        return bookingMapper.toResponse(booking);
    }

    public BookingResponse cancelBooking(UUID id) {
        BookingEntity booking = findOrThrow(id);
        assertOwnerOrAdmin(booking);
        if (booking.getStatus() != BookingStatus.PENDING && booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Only PENDING or CONFIRMED bookings can be cancelled");
        }
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingMapper.toResponse(booking);
    }

    public BookingResponse confirmBooking(UUID id) {
        BookingEntity booking = findOrThrow(id);
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException("Only PENDING bookings can be confirmed");
        }
        booking.setStatus(BookingStatus.CONFIRMED);
        return bookingMapper.toResponse(booking);
    }

    public BookingResponse completeBooking(UUID id) {
        BookingEntity booking = findOrThrow(id);
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Only CONFIRMED bookings can be completed");
        }
        booking.setStatus(BookingStatus.COMPLETED);
        return bookingMapper.toResponse(booking);
    }

    private BookingEntity findOrThrow(UUID id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    private UserEntity getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    }

    private void assertOwnerOrAdmin(BookingEntity booking) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin && !booking.getUser().getEmail().equals(auth.getName())) {
            throw new SecurityException("Access denied");
        }
    }
}
