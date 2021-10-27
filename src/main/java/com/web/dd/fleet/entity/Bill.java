package com.web.dd.fleet.entity;

import com.web.dd.fleet.payload.BillPayload;
import com.web.dd.fleet.utils.DateUtils;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "bills")
public class Bill implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String description;

    private String provider;

    private BillType type;

    private LocalDate validUntil;

    private LocalDate date;

    private BigDecimal price;

    @NotNull
    @Column(columnDefinition = "Timestamp default current_timestamp")
    private Timestamp timestamp;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image image;

    public static Bill createJobFromBillPayload(BillPayload billPayload, User user, Car car) {
        return Bill.builder()
                .description(billPayload.getDescription())
                .provider(billPayload.getProvider())
                .type(billPayload.getType())
                .validUntil(DateUtils.parseDate(billPayload.getValidUntil()))
                .date(DateUtils.parseDate(billPayload.getDate()))
                .price(billPayload.getPrice())
                .car(car)
                .timestamp(Timestamp.from(Instant.now()))
                .user(user)
                .build();
    }

    public void updateFromPayload(BillPayload billPayload) {
        setProvider(billPayload.getProvider());
        setDescription(billPayload.getDescription());
        setType(billPayload.getType());
        setValidUntil(LocalDate.parse(billPayload.getValidUntil(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        setDate(LocalDate.parse(billPayload.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        setPrice(billPayload.getPrice());
    }
}
