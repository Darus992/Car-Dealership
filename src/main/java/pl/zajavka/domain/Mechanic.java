package pl.zajavka.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.zajavka.infrastructure.database.entity.ServiceMechanicEntity;

import java.util.Set;

@With
@Value
@Builder
@ToString(of = {"mechanicId", "name", "surname", "pesel"})
@EqualsAndHashCode(of = "mechanicId")
public class Mechanic {

    Integer mechanicId;
    String name;
    String surname;
    String pesel;
    Set<ServiceMechanic> serviceMechanics;
}
