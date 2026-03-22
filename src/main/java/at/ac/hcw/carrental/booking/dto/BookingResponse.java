package at.ac.hcw.carrental.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private UUID id;
    private UUID userId;
    private String userEmail;
    private UUID carId;
    private String carBrand;
    private String carModel;
    private String carLicensePlate;
    private LocalDateTime pickupDateTime;
    private LocalDateTime returnDateTime;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createdAt;
}
