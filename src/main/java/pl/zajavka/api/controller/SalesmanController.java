package pl.zajavka.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.zajavka.api.dto.CarToBuyDTO;
import pl.zajavka.api.dto.MechanicDTO;
import pl.zajavka.api.dto.SalesmanDTO;
import pl.zajavka.api.dto.mapper.CarMapper;
import pl.zajavka.api.dto.mapper.MechanicMapper;
import pl.zajavka.api.dto.mapper.SalesmanMapper;
import pl.zajavka.business.CarPurchaseService;
import pl.zajavka.business.CarServiceRequestService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesmanController {

    private static final String SALESMAN = "/salesman";
    private final CarPurchaseService carPurchaseService;
    private final CarServiceRequestService carServiceRequestService;
    private final CarMapper carMapper;
    private final SalesmanMapper salesmanMapper;
    private final MechanicMapper mechanicMapper;

    @GetMapping(value = SALESMAN)
    public String homePage(Model model){
        List<CarToBuyDTO> availableCars = carPurchaseService.availableCars().stream()
                .map(carMapper::map)
                .toList();
        List<SalesmanDTO> availableSalesman = carPurchaseService.availableSalesman().stream()
                .map(salesmanMapper::map)
                .toList();
        List<MechanicDTO> availableMechanics = carServiceRequestService.availableMechanics().stream()
                .map(mechanicMapper::map)
                .toList();

        model.addAttribute("availableCarDTOs", availableCars);
        model.addAttribute("availableSalesmanDTOs", availableSalesman);
        model.addAttribute("availableMechanicDTOs", availableMechanics);
        return "salesman_portal";
    }
}
