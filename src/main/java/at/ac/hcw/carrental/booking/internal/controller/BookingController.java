package at.ac.hcw.carrental.booking.internal.controller;

import at.ac.hcw.carrental.booking.BookingService;
import at.ac.hcw.carrental.booking.dto.BookingResponse;
import at.ac.hcw.carrental.booking.dto.CreateBookingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking", description = "Manage car bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new booking")
    public BookingResponse createBooking(@Valid @RequestBody CreateBookingRequest request) {
        return bookingService.createBooking(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all bookings (Admin only)")
    public List<BookingResponse> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get current user's bookings")
    public List<BookingResponse> getMyBookings() {
        return bookingService.getMyBookings();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a booking by ID (owner or Admin)")
    public BookingResponse getBookingById(@PathVariable UUID id) {
        return bookingService.getBookingById(id);
    }

    @PatchMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Cancel a booking (owner or Admin)")
    public BookingResponse cancelBooking(@PathVariable UUID id) {
        return bookingService.cancelBooking(id);
    }

    @PatchMapping("/{id}/confirm")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Confirm a booking (Admin only)")
    public BookingResponse confirmBooking(@PathVariable UUID id) {
        return bookingService.confirmBooking(id);
    }

    @PatchMapping("/{id}/complete")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Complete a booking (Admin only)")
    public BookingResponse completeBooking(@PathVariable UUID id) {
        return bookingService.completeBooking(id);
    }
}
