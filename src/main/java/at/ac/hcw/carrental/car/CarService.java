package at.ac.hcw.carrental.car;

import at.ac.hcw.carrental.car.dto.CarResponse;
import at.ac.hcw.carrental.car.dto.CreateCarRequest;
import at.ac.hcw.carrental.car.dto.UpdateCarRequest;
import at.ac.hcw.carrental.car.internal.mapper.CarMapper;
import at.ac.hcw.carrental.car.internal.model.CarEntity;
import at.ac.hcw.carrental.car.internal.repository.CarRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository repository;
    private final CarMapper mapper;
    private final ObjectMapper patchMapper;

    @Transactional
    public CarResponse create(CreateCarRequest request){

        if(repository.existsByLicensePlate(request.getLicensePlate())){
            throw new IllegalArgumentException("Duplicate license plates not allowed");
        }

        CarEntity entity = CarEntity.builder()
                .brand(request.getBrand())
                .model(request.getModel())
                .carType(request.getCarType())
                .licensePlate(request.getLicensePlate())
                .year(request.getYear())
                .dailyRate(request.getDailyRate())
                .location(request.getLocation())
                .build();

        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<CarResponse> getAvailableCars(){
        return repository.findAllByAvailableTrue()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional
    public CarResponse updateCar(UUID carId, UpdateCarRequest request) throws JsonMappingException {

        CarEntity entity = repository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car with id " + carId + " does not exist"));

        patchMapper.updateValue(entity, request);

        return mapper.toResponse(entity);
    }

}
