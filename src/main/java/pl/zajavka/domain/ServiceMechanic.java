package pl.zajavka.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.zajavka.infrastructure.database.entity.CarServiceRequestEntity;
import pl.zajavka.infrastructure.database.entity.MechanicEntity;
import pl.zajavka.infrastructure.database.entity.ServiceEntity;

@With
@Value
@Builder
@ToString(of = {"serviceMechanicId", "hours", "comment"})
@EqualsAndHashCode(of = "serviceMechanicId")
public class ServiceMechanic {

    Integer serviceMechanicId;
    Integer hours;
    String comment;
    CarServiceRequest carServiceRequest;
    Mechanic mechanic;
    Service service;
}
