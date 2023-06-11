package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zajavka.domain.Mechanic;

import java.util.List;
//import org.springframework.transaction.annotation.Transactional;
//import pl.zajavka.business.dao.CarServiceRequestDAO;
//import pl.zajavka.business.management.FileDataPreparationService;
//import pl.zajavka.domain.CarServiceRequest;
//import pl.zajavka.domain.CarToBuy;
//import pl.zajavka.domain.CarToService;
//import pl.zajavka.domain.Customer;
//
//import java.time.OffsetDateTime;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import java.util.Set;
//import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarServiceRequestService {

    private final MechanicService mechanicService;
//    private final CarService carService;
//    private final CustomerService customerService;
//    private final CarServiceRequestDAO carServiceRequestDAO;
//
    public List<Mechanic> availableMechanics() {
        return mechanicService.findAvailableMechanics();
    }
//
//    public void requestService() {
//        Map<Boolean, List<CarServiceRequest>> serviceRequests = fileDataPreparationService.createCarServiceRequests().stream()
//                .collect(Collectors.groupingBy(element -> element.getCar().carBoughtHere()));
//
//        serviceRequests.get(true).forEach(this::saveServiceRequestsForExistingCar);
//        serviceRequests.get(false).forEach(this::saveServiceRequestsForNewCar);
//    }
//
//    private void saveServiceRequestsForExistingCar(CarServiceRequest request) {
//        CarToService car = carService.findCarToService(request.getCar().getVin())
//                .orElse(findInCarToBuyAndSaveInCarToService(request.getCar()));
//        Customer customer = customerService.findCustomer(request.getCustomer().getEmail());
//
//        CarServiceRequest carServiceRequest = buildCarServiceRequest(request, car, customer);
//        Set<CarServiceRequest> existingCarServiceRequests = customer.getCarServiceRequests();
//        existingCarServiceRequests.add(carServiceRequest);
//        customerService.saveServiceRequest(customer);
//    }
//
//    private CarToService findInCarToBuyAndSaveInCarToService(CarToService car) {
//        CarToBuy carToBuy = carService.findCarToBuy(car.getVin());
//        return carService.saveCarToService(carToBuy);
//    }
//
//    private void saveServiceRequestsForNewCar(CarServiceRequest request) {
//        CarToService car = carService.saveCarToService(request.getCar());
//        Customer customer = customerService.saveCustomer(request.getCustomer());
//
//        CarServiceRequest carServiceRequest = buildCarServiceRequest(request, car, customer);
//        Set<CarServiceRequest> existingCarServiceRequests = customer.getCarServiceRequests();
//        existingCarServiceRequests.add(carServiceRequest);
//        customerService.saveServiceRequest(customer.withCarServiceRequests(existingCarServiceRequests));
//    }
//
//    private CarServiceRequest buildCarServiceRequest(
//            CarServiceRequest request,
//            CarToService car,
//            Customer customer
//    ) {
//        OffsetDateTime when = OffsetDateTime.now();
//        return CarServiceRequest.builder()
//                .carServiceRequestNumber(generateCarServiceRequestNumber(when))
//                .receivedDateTime(when)
//                .customerComment(request.getCustomerComment())
//                .customer(customer)
//                .car(car)
//                .build();
//    }
//
//    private String generateCarServiceRequestNumber(OffsetDateTime when) {
//        return "%s.%s.%s-%s.%s.%s.%s".formatted(
//                when.getYear(),
//                when.getMonth().ordinal(),
//                when.getDayOfMonth(),
//                when.getHour(),
//                when.getMinute(),
//                when.getSecond(),
//                randomInt(10, 100)
//        );
//    }
//
//    @SuppressWarnings("SameParameterValue")
//    private int randomInt(int min, int max) {
//        return new Random().nextInt(max - min) + min;
//    }
//
//    @Transactional
//    public CarServiceRequest findAnyActiveServiceRequest(String carVin) {
//        Set<CarServiceRequest> serviceRequest = carServiceRequestDAO.findActiveServiceRequestByCarVin(carVin);
//
//        if (serviceRequest.size() != 1) {
//            throw new RuntimeException(
//                    "There should be only one active service request at a time, car vin: [%s]".formatted(carVin));
//        }
//        return serviceRequest.stream()
//                .findAny()
//                .orElseThrow(() -> new RuntimeException("Could not find any service request, car vin: [%s]".formatted(carVin)));
//    }
}
