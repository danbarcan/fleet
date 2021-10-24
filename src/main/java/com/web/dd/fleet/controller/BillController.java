package com.web.dd.fleet.controller;

import com.web.dd.fleet.payload.BillPayload;
import com.web.dd.fleet.payload.CarPayload;
import com.web.dd.fleet.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/bill")
public class BillController {

    private BillService billService;

    @Autowired
    public BillController(final BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/car/{carId}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<Integer, Set<BillPayload>>> getBillsByCarId(@PathVariable Long carId) {
        return billService.getBillsByCarId(carId);
    }

    @PostMapping()
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CarPayload> addBill(@Valid @RequestBody BillPayload billPayload) {
        return billService.saveBill(billPayload);
    }

    @PutMapping("/{billId}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CarPayload> updateBill(@PathVariable Long billId, @Valid @RequestBody BillPayload billPayload) {
        return billService.updateBill(billId, billPayload);
    }

    @DeleteMapping("/{billId}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CarPayload> deleteBill(@PathVariable Long billId) {
        return billService.deleteBill(billId);
    }
}
