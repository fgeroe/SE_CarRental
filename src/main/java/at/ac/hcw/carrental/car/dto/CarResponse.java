package at.ac.hcw.carrental.car.dto;

import at.ac.hcw.carrental.car.internal.model.CarType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarResponse {

    private UUID id;
    private String brand;
    private String model;
    private int  year;
    private BigDecimal dailyRate;
    private String licensePlate;
    private CarType carType;
    private String location;
    private boolean available;

}
