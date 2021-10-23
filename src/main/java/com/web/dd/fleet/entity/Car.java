package com.web.dd.fleet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "car")
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String model;

    @NotNull
    private String make;

    @NotNull
    private String type;

    @NotNull
    private String registration;

    private LocalDate itpDate;

    private LocalDate rcaDate;

    private LocalDate vignetteDate;

    private LocalDate cascoDate;

    private LocalDate revisionDate;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "car", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Bill> bills;

    @PreRemove
    public void preRemove() {
        setBills(null);
    }
}
