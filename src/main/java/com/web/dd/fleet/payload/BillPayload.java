package com.web.dd.fleet.payload;

import com.web.dd.fleet.entity.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BillPayload implements Serializable {
    private Long id;
    private String description;
    private BillType type;
    private LocalDate validUntil;
    private Timestamp timestamp;
    private User user;

    public static BillPayload createBillPayloadFromOperation(Bill b) {
        return BillPayload.builder()
                .id(b.getId())
                .description(b.getDescription())
                .type(b.getType())
                .validUntil(b.getValidUntil())
                .timestamp(b.getTimestamp())
                .build();
    }
}
