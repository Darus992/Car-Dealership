package pl.zajavka.domain;

import lombok.*;

import java.util.Objects;
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

    public Boolean carBoughtHere() {
        return  Objects.nonNull(vin)
                && Objects.isNull(brand)
                && Objects.isNull(model)
                && Objects.isNull(year);
    }
}
