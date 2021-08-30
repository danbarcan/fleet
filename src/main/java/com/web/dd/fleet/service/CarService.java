package com.web.dd.fleet.service;

import com.web.dd.fleet.entity.Car;
import com.web.dd.fleet.entity.User;
import com.web.dd.fleet.payload.CarPayload;
import com.web.dd.fleet.repository.CarRepository;
import com.web.dd.fleet.repository.OperationRepository;
import com.web.dd.fleet.repository.UserRepository;
import com.web.dd.fleet.security.UserPrincipal;
import com.web.dd.fleet.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private CarRepository carRepository;
    private UserRepository userRepository;
    private OperationRepository operationRepository;

    @Autowired
    public CarService(final CarRepository carRepository, final UserRepository userRepository, final OperationRepository operationRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.operationRepository = operationRepository;
    }

    public ResponseEntity<List<CarPayload>> getAllCarsForCurrentUser() {
        UserPrincipal userPrincipal = AppUtils.getCurrentUserDetails();
        Optional<User> user = userRepository.findById(userPrincipal.getId());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(mapListOfCarsToResponse(carRepository.findAllByUser(user.get())));
    }

    public ResponseEntity<CarPayload> getCarById(Long carId) {
        UserPrincipal userPrincipal = AppUtils.getCurrentUserDetails();
        Optional<User> user = userRepository.findById(userPrincipal.getId());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Optional<Car> optionalCar = carRepository.findById(carId);
        if (!optionalCar.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (!optionalCar.get().getUser().getId().equals(user.get().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(CarPayload.createJobResponsePayloadFromJob(optionalCar.get()));
    }

    public ResponseEntity<List<CarPayload>> saveCar(CarPayload carPayload) {
        UserPrincipal userPrincipal = AppUtils.getCurrentUserDetails();
        Optional<User> user = userRepository.findById(userPrincipal.getId());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Car car = Car.builder()
                .user(user.get())
                .make(carPayload.getMake())
                .model(carPayload.getModel())
                .build();

        carRepository.save(car);

        return ResponseEntity.ok(mapListOfCarsToResponse(carRepository.findAllByUser(user.get())));
    }

    public ResponseEntity<List<CarPayload>> updateCar(Long carId, CarPayload carPayload) {
        UserPrincipal userPrincipal = AppUtils.getCurrentUserDetails();
        Optional<User> user = userRepository.findById(userPrincipal.getId());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Optional<Car> carOptional = carRepository.findById(carId);
        if (!carOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Car car = carOptional.get();

        if (!car.getUser().getId().equals(user.get().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        car.setMake(carPayload.getMake());
        car.setModel(carPayload.getModel());

        carRepository.save(car);

        return ResponseEntity.ok(mapListOfCarsToResponse(carRepository.findAllByUser(car.getUser())));
    }

    public ResponseEntity<List<CarPayload>> deleteCar(Long carId) {
        Optional<Car> carOptional = carRepository.findById(carId);

        if (!carOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Car car = carOptional.get();
        UserPrincipal userPrincipal = AppUtils.getCurrentUserDetails();
        if (!userPrincipal.getId().equals(car.getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        car.getOperations().forEach(operation -> operationRepository.delete(operation));
        carRepository.delete(car);

        return ResponseEntity.ok(mapListOfCarsToResponse(carRepository.findAllByUser(car.getUser())));
    }

    private List<CarPayload> mapListOfCarsToResponse(List<Car> cars) {
        return cars.stream().map(CarPayload::createJobResponsePayloadFromJob).collect(Collectors.toList());
    }
}
