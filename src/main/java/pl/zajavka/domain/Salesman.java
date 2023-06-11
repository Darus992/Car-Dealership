package pl.zajavka.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@ToString(of = {"salesmanId", "name", "surname", "pesel"})
@EqualsAndHashCode(of = "pesel")
public class Salesman {

    Integer salesmanId;
    String name;
    String surname;
    String pesel;
    Set<Invoice> invoices;
}
