package com.web.dd.fleet.payload;

import com.web.dd.fleet.entity.Bill;
import com.web.dd.fleet.entity.Car;
import com.web.dd.fleet.utils.DateUtils;
import lombok.*;

import java.io.Serializable;
import java.util.Map;
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
    private Map<Integer, Set<BillPayload>> bills;

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
                .bills(car.getBills() == null ? null : getListOfBillsByYear(car.getBills()))
                .itpDate(DateUtils.toString(car.getItpDate()))
                .rcaDate(DateUtils.toString(car.getRcaDate()))
                .vignetteDate(DateUtils.toString(car.getVignetteDate()))
                .cascoDate(DateUtils.toString(car.getCascoDate()))
                .revisionDate(DateUtils.toString(car.getRevisionDate()))
                .build();
    }

    private static Map<Integer, Set<BillPayload>> getListOfBillsByYear(Set<Bill> bills) {
        return bills.stream()
                .map(BillPayload::createBillPayloadFromOperation)
                .collect(Collectors.groupingBy(e -> DateUtils.parseDate(e.getDate()).getYear(),
                        Collectors.mapping(e -> e, Collectors.toSet())));
    }

}
