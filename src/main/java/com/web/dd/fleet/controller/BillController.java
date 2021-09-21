package com.web.dd.fleet.controller;

import com.web.dd.fleet.payload.BillPayload;
import com.web.dd.fleet.payload.CarPayload;
import com.web.dd.fleet.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/bill")
public class BillController {

    private BillService billService;

    @Autowired
    public BillController(final BillService billService) {
        this.billService = billService;
    }

    @PostMapping()
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CarPayload> addBill(@Valid @RequestBody BillPayload billPayload) {
        return billService.saveBill(billPayload);
    }

    @PutMapping("/{billId}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CarPayload> updateCar(@PathVariable Long billId, @Valid @RequestBody BillPayload billPayload) {
        return billService.updateBill(billId, billPayload);
    }

    @DeleteMapping("/{billId}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CarPayload> deleteCar(@PathVariable Long billId) {
        return billService.deleteBill(billId);
    }
}
