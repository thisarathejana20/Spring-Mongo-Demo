package com.personnel.user.demo.commons;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
@Setter
public class JwtContent {
    private String subject;
    private Map<String, String> payload;
    private float expiredIn;
}
