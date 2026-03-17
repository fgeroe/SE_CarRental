package at.ac.hcw.carrental.car.internal.controller;

import at.ac.hcw.carrental.car.CarService;
import at.ac.hcw.carrental.car.dto.CarResponse;
import at.ac.hcw.carrental.car.dto.CreateCarRequest;
import at.ac.hcw.carrental.car.dto.UpdateCarRequest;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/car")
@RequiredArgsConstructor
@Tag(name = "Car", description = "Manage cars")
public class CarController {

    private final CarService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new car")
    public CarResponse create(@Valid @RequestBody CreateCarRequest request) {
        return service.create(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all available cars")
    public List<CarResponse> getAvailableCars() {
        return service.getAvailableCars();
    }

    @PatchMapping("/{carId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update information of a car")
    public CarResponse update(@PathVariable UUID carId, @Valid @RequestBody UpdateCarRequest request) throws JsonMappingException {
        return service.updateCar(carId, request);
    }
}
