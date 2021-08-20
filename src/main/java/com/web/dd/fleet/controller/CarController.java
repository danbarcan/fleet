package com.web.dd.fleet.controller;

import com.web.dd.fleet.payload.CarPayload;
import com.web.dd.fleet.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(final CarService carService) {
        this.carService = carService;
    }

    @GetMapping()
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<CarPayload>> getAllCars() {
        return carService.getAllCarsForCurrentUser();
    }

    @DeleteMapping("/{carId}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<CarPayload>> deleteCar(@PathVariable Long carId) {
        return carService.deleteCar(carId);
    }

    @PostMapping()
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<CarPayload>> saveCar(@Valid @RequestBody CarPayload carPayload) {
        return carService.saveCar(carPayload);
    }

    @PutMapping("/{carId}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<CarPayload>> updateCar(@PathVariable Long carId, @Valid @RequestBody CarPayload carPayload) {
        return carService.updateCar(carId, carPayload);
    }
}
