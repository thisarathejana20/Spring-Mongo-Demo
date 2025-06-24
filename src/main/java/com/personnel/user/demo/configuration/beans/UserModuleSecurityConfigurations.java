package com.personnel.user.demo.configuration.beans;

import java.util.Set;

public class UserModuleSecurityConfigurations {
    // ✅ These are routes allowed even if user is not yet verified
    public static final Set<String> ACTIVATION_NOT_REQUIRED_URLS = Set.of(
            "/api/v1/auth/resend-otp",
            "/api/v1/auth/verify-otp",
            "/api/v1/auth/logout",
            "/api/v1/user/profile",
            "/health" // any public route like health check, docs, etc.
    );

    private UserModuleSecurityConfigurations() {
        // Private constructor to prevent instantiation
    }
}
