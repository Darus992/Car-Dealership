package pl.zajavka.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@ToString(of = {"serviceId", "serviceCode", "description", "price"})
@EqualsAndHashCode(of = "serviceId")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Integer serviceId;


    @Column(name = "service_code", unique = true)
    private String serviceCode;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "service")
    private Set<ServiceMechanicEntity> serviceMechanics;
}
