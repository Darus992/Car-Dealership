package pl.zajavka.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.zajavka.api.dto.*;
import pl.zajavka.api.dto.mapper.CarServiceRequestMapper;
import pl.zajavka.api.dto.mapper.MechanicMapper;
import pl.zajavka.api.dto.mapper.PartMapper;
import pl.zajavka.api.dto.mapper.ServiceMapper;
import pl.zajavka.business.CarServiceProcessingService;
import pl.zajavka.business.CarServiceRequestService;
import pl.zajavka.business.PartCatalogService;
import pl.zajavka.business.ServiceCatalogService;
import pl.zajavka.domain.CarServiceProcessingRequest;
import pl.zajavka.domain.Part;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class MechanicController {

    private static final String MECHANIC = "/mechanic";
    private static final String MECHANIC_WORK_UNIT = "/mechanic/workUnit";

    private final CarServiceProcessingService carServiceProcessingService;
    private final CarServiceRequestService carServiceRequestService;
    private final PartCatalogService partCatalogService;
    private final ServiceCatalogService serviceCatalogService;
    private final CarServiceRequestMapper carServiceRequestMapper;
    private final MechanicMapper mechanicMapper;
    private final PartMapper partMapper;
    private final ServiceMapper serviceMapper;


    @GetMapping(value = MECHANIC)
    public ModelAndView mechanicCheckPage(){
        Map<String, ?> data = prepareNecessaryData();
        return new ModelAndView("mechanic_service", data);
    }

    @PostMapping(value = MECHANIC_WORK_UNIT)
    public String mechanicWorkUnit(
            @ModelAttribute("carServiceRequestProcessUnitDTO") CarServiceMechanicProcessingUnitDTO dto,
            BindingResult bindingResult,
            ModelMap modelMap
    ){
        if(bindingResult.hasErrors()){
            return "error";
        }
        CarServiceProcessingRequest request = carServiceRequestMapper.map(dto);
        carServiceProcessingService.process(request);
        if(dto.getDone()){
            return "mechanic_service_done";
        }else {
            modelMap.addAllAttributes(prepareNecessaryData());
            return "redirect:/mechanic";
        }
    }

    private Map<String, ?> prepareNecessaryData() {
        List<CarServiceRequestDTO> availableServiceRequests = getAvailableServiceRequests();
        List<String> availableCarVins = availableServiceRequests.stream()
                .map(CarServiceRequestDTO::getCarVin)
                .toList();
        List<MechanicDTO> availableMechanics = getAvailableMechanics();
        List<String> availableMechanicPesels = availableMechanics.stream()
                .map(MechanicDTO::getPesel)
                .toList();
        List<PartDTO> parts = findParts();
        List<String> partSerialNumbers = preparePartSerialNumbers(parts);
        List<ServiceDTO> services = findService();
        List<String> serviceCodes = services.stream()
                .map(ServiceDTO::getServiceCode)
                .toList();

        return Map.of(
                "availableServiceRequestsDTOs", availableServiceRequests,
                "availableCarVins", availableCarVins,
                "availableMechanicsDTOs", availableMechanics,
                "availableMechanicPesels", availableMechanicPesels,
                "partDTOs", parts,
                "partSerialNumbers", partSerialNumbers,
                "serviceDTOs", services,
                "serviceCodes", serviceCodes,
                "carServiceProcessDTO", CarServiceMechanicProcessingUnitDTO.buildDefault()
        );
    }

    private List<CarServiceRequestDTO> getAvailableServiceRequests(){
        return carServiceRequestService.availableServiceRequests().stream()
                .map(carServiceRequestMapper::map)
                .toList();
    }

    private List<MechanicDTO> getAvailableMechanics(){
        return carServiceRequestService.availableMechanics().stream()
                .map(mechanicMapper::map)
                .toList();
    }

    private List<PartDTO> findParts(){
        return partCatalogService.findAll().stream()
                .map(partMapper::map)
                .toList();
    }

    private List<ServiceDTO> findService() {
        return serviceCatalogService.findAdd().stream()
                .map(serviceMapper::map)
                .toList();
    }

    private List<String> preparePartSerialNumbers(List<PartDTO> parts) {
        List<String> partSerialNumbers = new ArrayList<>(parts.stream()
                .map(PartDTO::getSerialNumber)
                .toList());
        partSerialNumbers.add(Part.NONE);
        return partSerialNumbers;
    }
}
