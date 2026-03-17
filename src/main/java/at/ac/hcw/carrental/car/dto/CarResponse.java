package at.ac.hcw.carrental.car.dto;

import at.ac.hcw.carrental.car.internal.model.CarType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarResponse {

    private String brand;
    private String model;
    private int  year;
    private BigDecimal dailyRate;
    private String licensePlate;
    private CarType carType;
    private String location;
    private boolean available;

}
