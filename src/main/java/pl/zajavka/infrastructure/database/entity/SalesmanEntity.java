package pl.zajavka.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@ToString(of = {"salesmanId", "name", "surname", "pesel"})
@EqualsAndHashCode(of = "salesmanId")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "salesman")
public class SalesmanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salesman_id")
    private Integer salesmanId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "pesel", unique = true)
    private String pesel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "salesman")
    private Set<InvoiceEntity> invoices;
}
