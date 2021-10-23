package com.web.dd.fleet.payload;

import com.web.dd.fleet.entity.Bill;
import com.web.dd.fleet.entity.BillType;
import com.web.dd.fleet.entity.User;
import com.web.dd.fleet.utils.DateUtils;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BillPayload implements Serializable {
    private Long id;
    private Long carId;
    private String provider;
    private String description;
    private BillType type;
    private String validUntil;
    private String date;
    private BigDecimal price;
    private Timestamp timestamp;
    private User user;

    public static BillPayload createBillPayloadFromOperation(Bill b) {
        return BillPayload.builder()
                .id(b.getId())
                .carId(b.getCar().getId())
                .provider(b.getProvider())
                .description(b.getDescription())
                .type(b.getType())
                .validUntil(DateUtils.toString(b.getValidUntil()))
                .date(DateUtils.toString(b.getDate()))
                .price(b.getPrice())
                .timestamp(b.getTimestamp())
                .build();
    }
}
