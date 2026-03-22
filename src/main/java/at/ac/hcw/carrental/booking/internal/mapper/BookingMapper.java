package at.ac.hcw.carrental.booking.internal.mapper;

import at.ac.hcw.carrental.booking.dto.BookingResponse;
import at.ac.hcw.carrental.booking.internal.model.BookingEntity;
import at.ac.hcw.carrental.booking.internal.model.BookingStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "car.id", target = "carId")
    @Mapping(source = "car.brand", target = "carBrand")
    @Mapping(source = "car.model", target = "carModel")
    @Mapping(source = "car.licensePlate", target = "carLicensePlate")
    @Mapping(source = "status", target = "status", qualifiedByName = "statusToString")
    BookingResponse toResponse(BookingEntity entity);

    @Named("statusToString")
    default String statusToString(BookingStatus status) {
        return status == null ? null : status.name();
    }
}
