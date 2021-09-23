package com.web.dd.fleet.payload;

import com.web.dd.fleet.entity.Car;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CarPayload implements Serializable {
    private Long id;
    private String model;
    private String make;
    private String type;
    private String registration;
    private Set<OperationPayload> operations;
    private Set<BillPayload> bills;

    private String itpDate;
    private String rcaDate;
    private String vignetteDate;
    private String cascoDate;
    private String revisionDate;

    public static CarPayload createJobResponsePayloadFromJob(Car car) {
        return CarPayload.builder()
                .id(car.getId())
                .model(car.getModel())
                .make(car.getMake())
                .type(car.getType())
                .registration(car.getRegistration())
//                .operations(car.getOperations() == null ? null : car.getOperations().stream().map(OperationPayload::createOperationPayloadFromOperation).collect(Collectors.toSet()))
                .bills(car.getBills() == null ? null : car.getBills().stream().map(BillPayload::createBillPayloadFromOperation).collect(Collectors.toSet()))
                .itpDate(car.getItpDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .rcaDate(car.getRcaDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .vignetteDate(car.getVignetteDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .cascoDate(car.getCascoDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .revisionDate(car.getRevisionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
    }
}
