package pl.zajavka.domain;

import lombok.*;

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
