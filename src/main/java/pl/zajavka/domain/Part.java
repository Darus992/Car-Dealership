package pl.zajavka.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@With
@Value
@Builder
@ToString(of = {"partId", "serialNumber", "description", "price"})
@EqualsAndHashCode(of = "serialNumber")
public class Part {

    public static final String NONE = "NONE";

    Integer partId;
    String serialNumber;
    String description;
    BigDecimal price;
    Set<ServicePart> serviceParts;
}
