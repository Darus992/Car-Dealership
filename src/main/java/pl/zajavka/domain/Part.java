package pl.zajavka.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.zajavka.infrastructure.database.entity.ServicePartEntity;

import java.math.BigDecimal;
import java.util.Set;

@With
@Value
@Builder
@ToString(of = {"partId", "serialNumber", "description", "price"})
@EqualsAndHashCode(of = "partId")
public class Part {

    Integer partId;
    String serialNumber;
    String description;
    BigDecimal price;
    Set<ServicePart> serviceParts;
}
