package com.web.dd.fleet.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.dd.fleet.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    public static UserPayload createUserPayloadFromUser(User user) {
        return UserPayload.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}