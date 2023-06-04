package pl.zajavka.domain;


import lombok.*;

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
