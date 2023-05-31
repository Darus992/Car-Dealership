package pl.zajavka.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.zajavka.infrastructure.database.entity.CarToBuyEntity;
import pl.zajavka.infrastructure.database.entity.CustomerEntity;
import pl.zajavka.infrastructure.database.entity.SalesmanEntity;

import java.time.OffsetDateTime;

@With
@Value
@Builder
@ToString(of = {"invoiceId", "invoiceNumber", "dateTime"})
@EqualsAndHashCode(of = "invoiceId")
public class Invoice {

    Integer invoiceId;
    String invoiceNumber;
    OffsetDateTime dateTime;
    CarToBuy car;
    Customer customer;
    Salesman salesman;
}
