package pl.zajavka.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.zajavka.infrastructure.database.entity.CarServiceRequestEntity;

import java.util.Set;

@With
@Value
@Builder
@ToString(of = {"carToServiceId", "vin", "brand", "model", "year"})
@EqualsAndHashCode(of = "carToServiceId")
public class CarToService {


    Integer carToServiceId;
    String vin;
    String brand;
    String model;
    Integer year;
    Set<CarServiceRequest> carServiceRequests;
}
