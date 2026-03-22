package at.ac.hcw.carrental.car.dto;

import at.ac.hcw.carrental.car.internal.model.CarType;
import at.ac.hcw.carrental.car.internal.model.Transmission;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateCarRequest {

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "License plate is required")
    private String licensePlate;

    @Min(value = 1950, message = "Year must be 1950 or later")
    @Max(value = 2026, message = "Year cannot be in the future")
    private int year;

    @NotNull(message = "Daily rate is required")
    @Positive(message = "Daily rate must be positive")
    private BigDecimal dailyRate;

    @NotNull(message = "Car type is required")
    private CarType carType;

    @NotBlank(message = "Location is required")
    private String location;

    @Min(value = 1, message = "Seats must be at least 1")
    @Max(value = 9, message = "Seats cannot exceed 9")
    private int seats;

    @NotNull(message = "Transmission type is required")
    private Transmission transmission;

    @Min(value = 0, message = "Large luggage space cannot be negative")
    private int largeLuggageSpace;

    @Min(value = 0, message = "Small luggage space cannot be negative")
    private int smallLuggageSpace;

    private String imageUrl;
}
