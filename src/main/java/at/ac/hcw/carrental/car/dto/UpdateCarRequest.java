package at.ac.hcw.carrental.car.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateCarRequest {

    private String brand;
    private String model;
    private Integer year;
    private String licensePlate;
    private String carType;
    private BigDecimal dailyRate;
    private String location;
    private Boolean available;
    private Integer seats;
    private String transmission;
    private Integer largeLuggageSpace;
    private Integer smallLuggageSpace;
    private String imageUrl;

}
