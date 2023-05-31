package pl.zajavka.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.zajavka.infrastructure.database.entity.AddressEntity;
import pl.zajavka.infrastructure.database.entity.CarServiceRequestEntity;
import pl.zajavka.infrastructure.database.entity.InvoiceEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@With
@Value
@Builder
@ToString(of = {"customerId", "name", "surname", "email"})
@EqualsAndHashCode(of = "customerId")
public class Customer {

    Integer customerId;
    String name;
    String surname;
    String phone;
    String email;
    Address address;
    Set<Invoice> invoices;
    Set<CarServiceRequest> carServiceRequests;
}
