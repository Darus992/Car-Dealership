package pl.zajavka.domain;

import lombok.*;

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


