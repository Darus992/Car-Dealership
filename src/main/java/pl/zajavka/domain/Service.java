package pl.zajavka.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.zajavka.infrastructure.database.entity.ServiceMechanicEntity;

import java.math.BigDecimal;
import java.util.Set;

@With
@Value
@Builder
@ToString(of = {"serviceId", "serviceCode", "description", "price"})
@EqualsAndHashCode(of = "serviceId")
public class Service {

    Integer serviceId;
    String serviceCode;
    String description;
    BigDecimal price;
    Set<ServiceMechanic> serviceMechanics;
}
