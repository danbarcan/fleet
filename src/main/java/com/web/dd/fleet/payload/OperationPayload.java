package com.web.dd.fleet.payload;

import com.web.dd.fleet.entity.Image;
import com.web.dd.fleet.entity.Operation;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OperationPayload implements Serializable {
    private Long id;
    private String name;
    private String operatorName;
    private BigDecimal price;
    private String comments;
    private MultipartFile imageFile;
    private Image image;

    public static OperationPayload createOperationPayloadFromOperation(Operation operation) {
        return OperationPayload.builder()
                .id(operation.getId())
                .name(operation.getName())
                .operatorName(operation.getOperatorName())
                .price(operation.getPrice())
                .comments(operation.getComments())
                .image(operation.getImage())
                .build();
    }
}
