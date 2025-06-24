package com.personnel.user.demo.configuration.beans;

import com.personnel.user.demo.configuration.beans.jwt.JwtSecurityFilter;
import com.personnel.user.demo.repository.UserRepopository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final BeanConfiguration beanConfiguration;
    private final AuthenticationProvider authenticationProvider;
    private final UserRepopository userRepopository;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationManager authenticationManager) throws Exception {
        return http.
                csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("auth/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtSecurityFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class).
                build();
    }

    @Bean
    public JwtSecurityFilter jwtSecurityFilter(AuthenticationManager authenticationManager) {
        return new JwtSecurityFilter(authenticationManager, userDetailsService,userRepopository);
    }
}
