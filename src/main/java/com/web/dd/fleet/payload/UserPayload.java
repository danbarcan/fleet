package com.web.dd.fleet.payload;

import com.web.dd.fleet.entity.User;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserPayload implements Serializable {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private Boolean emailNotification;
    private Boolean smsNotification;

    public static UserPayload createUserPayloadFromUser(User user) {
        return UserPayload.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .emailNotification(user.getEmailNotification())
                .smsNotification(user.getSmsNotification())
                .build();
    }
}