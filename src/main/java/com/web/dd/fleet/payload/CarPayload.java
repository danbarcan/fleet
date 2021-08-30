package com.web.dd.fleet.payload;

import com.web.dd.fleet.entity.Car;
import lombok.*;

import java.io.Serializable;
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
    private Set<OperationPayload> operations;

    public static CarPayload createJobResponsePayloadFromJob(Car car) {
        return CarPayload.builder()
                .id(car.getId())
                .model(car.getModel())
                .make(car.getMake())
                .operations(car.getOperations() == null ? null : car.getOperations().stream().map(OperationPayload::createOperationPayloadFromOperation).collect(Collectors.toSet()))
                .build();
    }
}
