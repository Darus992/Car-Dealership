package pl.zajavka.domain;

import lombok.*;

import java.time.OffsetDateTime;

@With
@Value
@Builder
@ToString(of = {"invoiceId", "invoiceNumber", "dateTime"})
@EqualsAndHashCode(of = "invoiceNumber")
public class Invoice {

    Integer invoiceId;
    String invoiceNumber;
    OffsetDateTime dateTime;
    CarToBuy car;
    Customer customer;
    Salesman salesman;
}
