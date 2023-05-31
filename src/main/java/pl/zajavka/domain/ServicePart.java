package pl.zajavka.domain;


import jakarta.persistence.*;
import lombok.*;
import pl.zajavka.infrastructure.database.entity.CarServiceRequestEntity;
import pl.zajavka.infrastructure.database.entity.PartEntity;

@With
@Value
@Builder
@ToString(of = {"servicePartId", "quantity"})
@EqualsAndHashCode(of = "servicePartId")
public class ServicePart {

    Integer servicePartId;
    Integer quantity;
    CarServiceRequest carServiceRequest;
    Part part;
}
