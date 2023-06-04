package pl.zajavka.domain;

import lombok.*;

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
