package com.personnel.user.demo.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignInResponse {
    private String token;
    private String userId;
}
