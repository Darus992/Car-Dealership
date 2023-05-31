package pl.zajavka.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zajavka.infrastructure.database.entity.CarToServiceEntity;

@Repository
public interface CarServiceRequestJpaRepository extends JpaRepository<CarToServiceEntity, Integer> {
}
