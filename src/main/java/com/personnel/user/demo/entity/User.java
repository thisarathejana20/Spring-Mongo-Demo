package com.personnel.user.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Document(collection = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String email;
    private String password;
    private String role;

    private UserStatus status; // 🔥 ADDED for checking token access (PENDING, TO_DELETE, etc.)

    // 🔥 Optional: enum for user status (this is used in filter)
    public enum UserStatus {
        ACTIVE,
        PENDING_VERIFICATION,
        TO_DELETE
    }

    // 🔥 Optional helper: determine if user is Admin or Tenant
    public boolean isAnyAdmin() {
        return "ADMIN".equalsIgnoreCase(this.role) || "SUPER_ADMIN".equalsIgnoreCase(this.role);
    }

    public boolean isTenant() {
        return "TENANT".equalsIgnoreCase(this.role);
    }

    public String getUserType() {
        return this.role;
    }
}
