package com.personnel.user.demo.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalConfig {
    private String secretKey;
    private boolean debugModeOn;
}
