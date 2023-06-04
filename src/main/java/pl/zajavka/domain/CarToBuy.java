package pl.zajavka.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Value
@Builder
@ToString(of = {"carToBuyId", "vin", "brand", "model", "year"})
@EqualsAndHashCode(of = "carToBuyId")
public class CarToBuy {

    Integer carToBuyId;
    String vin;
    String brand;
    String model;
    Integer year;
    String color;
    BigDecimal price;
    Invoice invoice;
}

