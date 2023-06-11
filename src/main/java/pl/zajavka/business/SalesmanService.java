package pl.zajavka.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.business.dao.SalesmanDAO;
import pl.zajavka.domain.Salesman;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class SalesmanService {

    private final SalesmanDAO salesmanDAO;

    @Transactional
    public Salesman findSalesman(String pesel) {
        Optional<Salesman> salesman = salesmanDAO.findByPesel(pesel);
        if (salesman.isEmpty()) {
            throw new RuntimeException("Could not find salesman by pesel: [%s]".formatted(pesel));
        }
        return salesman.get();
    }

    @Transactional
    public List<Salesman> findAvailableSalesman() {
        List<Salesman> availableSalesman = salesmanDAO.findAvailable();
        log.info("Available salesman: [{}]", availableSalesman.size());
        return availableSalesman;
    }
}
