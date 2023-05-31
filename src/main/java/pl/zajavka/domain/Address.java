package pl.zajavka.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.zajavka.infrastructure.database.entity.CustomerEntity;

@With
@Value
@Builder
@ToString(of = {"addressId", "country", "city", "postalCode", "address"})
@EqualsAndHashCode(of = "addressId")
public class Address {

    Integer addressId;
    String country;
    String city;
    String postalCode;
    String address;
    Customer customer;
}


