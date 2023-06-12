package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.business.dao.CarServiceRequestDAO;
import pl.zajavka.domain.*;
import pl.zajavka.domain.exception.ProcessingException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@AllArgsConstructor
public class CarServiceRequestService {

    private final MechanicService mechanicService;
    private final CarService carService;
    private final CustomerService customerService;
    private final CarServiceRequestDAO carServiceRequestDAO;


    public List<Mechanic> availableMechanics() {
        return mechanicService.findAvailableMechanics();
    }

    @Transactional
    public void makeServiceRequest(CarServiceRequest serviceRequest) {
        if (serviceRequest.getCar().shouldExistsInCarToBuy()) {
            saveServiceRequestsForExistingCar(serviceRequest);
        } else {
            saveServiceRequestsForNewCar(serviceRequest);
        }
    }

    private void saveServiceRequestsForExistingCar(CarServiceRequest serviceRequest) {
        validate(serviceRequest.getCar().getVin());

        CarToService car = carService.findCarToService(serviceRequest.getCar().getVin())
                .orElse(findInCarToBuyAndSaveInCarToService(serviceRequest.getCar()));
        Customer customer = customerService.findCustomer(serviceRequest.getCustomer().getEmail());

        CarServiceRequest carServiceRequest = buildCarServiceRequest(serviceRequest, car, customer);
        Set<CarServiceRequest> existingCarServiceRequests = customer.getCarServiceRequests();
        existingCarServiceRequests.add(carServiceRequest);
        customerService.saveServiceRequest(customer);
    }

    private void saveServiceRequestsForNewCar(CarServiceRequest serviceRequest) {
        validate(serviceRequest.getCar().getVin());

        CarToService car = carService.saveCarToService(serviceRequest.getCar());
        Customer customer = customerService.saveCustomer(serviceRequest.getCustomer());

        CarServiceRequest carServiceRequest = buildCarServiceRequest(serviceRequest, car, customer);
        Set<CarServiceRequest> existingCarServiceRequests = customer.getCarServiceRequests();
        existingCarServiceRequests.add(carServiceRequest);
        customerService.saveServiceRequest(customer.withCarServiceRequests(existingCarServiceRequests));
    }

    private void validate(String vin) {
        Set<CarServiceRequest> serviceRequests = carServiceRequestDAO.findActiveServiceRequestByCarVin(vin);
        if(serviceRequests.size() == 1){
            throw new ProcessingException(
                    "There should bo only one active service request at a time, car vin: [%s]".formatted(vin)
            );
        }
    }

    private CarToService findInCarToBuyAndSaveInCarToService(CarToService car) {
        CarToBuy carToBuy = carService.findCarToBuy(car.getVin());
        return carService.saveCarToService(carToBuy);
    }

    private CarServiceRequest buildCarServiceRequest(
            CarServiceRequest request,
            CarToService car,
            Customer customer
    ) {
        OffsetDateTime when = OffsetDateTime.now();
        return CarServiceRequest.builder()
                .carServiceRequestNumber(generateCarServiceRequestNumber(when))
                .receivedDateTime(when)
                .customerComment(request.getCustomerComment())
                .customer(customer)
                .car(car)
                .build();
    }

    private String generateCarServiceRequestNumber(OffsetDateTime when) {
        return "%s.%s.%s-%s.%s.%s.%s".formatted(
                when.getYear(),
                when.getMonth().ordinal(),
                when.getDayOfMonth(),
                when.getHour(),
                when.getMinute(),
                when.getSecond(),
                randomInt(10, 100)
        );
    }

    @SuppressWarnings("SameParameterValue")
    private int randomInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    @Transactional
    public CarServiceRequest findAnyActiveServiceRequest(String carVin) {
        Set<CarServiceRequest> serviceRequest = carServiceRequestDAO.findActiveServiceRequestByCarVin(carVin);

        if (serviceRequest.size() != 1) {
            throw new RuntimeException(
                    "There should be only one active service request at a time, car vin: [%s]".formatted(carVin));
        }
        return serviceRequest.stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException("Could not find any service request, car vin: [%s]".formatted(carVin)));
    }
}
