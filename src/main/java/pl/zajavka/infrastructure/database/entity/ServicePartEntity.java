package pl.zajavka.infrastructure.database.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@ToString(of = {"servicePartId", "quantity"})
@EqualsAndHashCode(of = "servicePartId")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_part")
public class ServicePartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_part_id")
    private Integer servicePartId;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_service_request_id")
    private CarServiceRequestEntity carServiceRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private PartEntity part;
}
