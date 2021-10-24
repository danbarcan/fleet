package com.web.dd.fleet.service;

import com.web.dd.fleet.entity.Bill;
import com.web.dd.fleet.entity.Car;
import com.web.dd.fleet.entity.User;
import com.web.dd.fleet.payload.BillPayload;
import com.web.dd.fleet.payload.CarPayload;
import com.web.dd.fleet.repository.BillRepository;
import com.web.dd.fleet.repository.CarRepository;
import com.web.dd.fleet.repository.UserRepository;
import com.web.dd.fleet.security.UserPrincipal;
import com.web.dd.fleet.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class BillService {

    private CarRepository carRepository;
    private UserRepository userRepository;
    private BillRepository billRepository;

    @Autowired
    public BillService(final CarRepository carRepository, final UserRepository userRepository, final BillRepository billRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.billRepository = billRepository;
    }

    public ResponseEntity<CarPayload> saveBill(BillPayload billPayload) {
        UserPrincipal userPrincipal = AppUtils.getCurrentUserDetails();
        Optional<User> user = userRepository.findById(userPrincipal.getId());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Optional<Car> carOptional = carRepository.findById(billPayload.getCarId());
        if (!carOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Car car = carOptional.get();

        if (!car.getUser().getId().equals(user.get().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Bill bill = Bill.createJobFromBillPayload(billPayload, user.get(), car);

        billRepository.save(bill);

        return ResponseEntity.ok(CarPayload.createJobResponsePayloadFromJob(carRepository.getById(billPayload.getCarId())));
    }

    public ResponseEntity<CarPayload> updateBill(Long billId, BillPayload billPayload) {
        UserPrincipal userPrincipal = AppUtils.getCurrentUserDetails();
        Optional<User> user = userRepository.findById(userPrincipal.getId());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Optional<Bill> billOptional = billRepository.findById(billId);

        if (!billOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Bill bill = billOptional.get();

        if (!bill.getUser().getId().equals(user.get().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        bill.updateFromPayload(billPayload);

        billRepository.save(bill);

        return ResponseEntity.ok(CarPayload.createJobResponsePayloadFromJob(carRepository.getById(bill.getCar().getId())));
    }

    public ResponseEntity<CarPayload> deleteBill(Long billId) {
        Optional<Bill> billOptional = billRepository.findById(billId);

        if (!billOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Bill bill = billOptional.get();
        UserPrincipal userPrincipal = AppUtils.getCurrentUserDetails();
        if (!userPrincipal.getId().equals(bill.getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        billRepository.delete(bill);

        return ResponseEntity.ok(CarPayload.createJobResponsePayloadFromJob(carRepository.getById(bill.getCar().getId())));
    }

    public ResponseEntity<Map<Integer, Set<BillPayload>>> getBillsByCarId(Long carId) {
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

        CarPayload carPayload = CarPayload.createJobResponsePayloadFromJob(car);

        return ResponseEntity.ok(carPayload.getBills());
    }
}
