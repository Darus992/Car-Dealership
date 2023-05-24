package pl.zajavka.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@ToString(of = {"mechanicId", "name", "surname", "pesel"})
@EqualsAndHashCode(of = "mechanicId")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mechanic")
public class MechanicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mechanic_id")
    private Integer mechanicId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "pesel", unique = true)
    private String pesel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mechanic")
    private Set<ServiceMechanicEntity> serviceMechanics;
}
