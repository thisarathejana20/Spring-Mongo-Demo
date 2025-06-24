package com.personnel.user.demo.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserModuleConfig {
    private int tokenExpireTimeWeb;  // in hours
    private int tokenExpireTime;     // in seconds
}
