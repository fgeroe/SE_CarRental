package at.ac.hcw.carrental.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreateBookingRequest {

    @NotNull(message = "Car ID is required")
    private UUID carId;

    @NotNull(message = "Pickup date and time is required")
    @FutureOrPresent(message = "Pickup must be now or in the future")
    private LocalDateTime pickupDateTime;

    @NotNull(message = "Return date and time is required")
    @Future(message = "Return must be in the future")
    private LocalDateTime returnDateTime;
}
