package com.web.dd.fleet.entity;

import com.web.dd.fleet.payload.BillPayload;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "bill")
public class Bill implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String description;

    @NotNull
    private BillType type;

    @NotNull
    private LocalDate validUntil;

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

    public static Bill createJobFromJobPayload(BillPayload billPayload, User user, Car car) {
        return Bill.builder()
                .description(billPayload.getDescription())
                .type(billPayload.getType())
                .validUntil(billPayload.getValidUntil())
                .car(car)
                .timestamp(Timestamp.from(Instant.now()))
                .user(user)
                .build();
    }
}
