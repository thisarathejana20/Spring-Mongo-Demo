package com.personnel.user.demo.configuration.beans.jwt;

import com.personnel.user.demo.commons.*;
import com.personnel.user.demo.configuration.beans.UserModuleSecurityConfigurations;
import com.personnel.user.demo.entity.User;
import com.personnel.user.demo.repository.UserRepopository;
import com.personnel.user.demo.service.UserService;
import com.personnel.user.demo.utill.exception.UnauthorizeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
public class JwtSecurityFilter extends BasicAuthenticationFilter {
    private final UserDetailsService userService;
    GlobalConfig globalConfigs = new GlobalConfig("secret", true);
    UserModuleConfig userModuleConfigs = new UserModuleConfig(10, 10);
    RequestDataProvider requestDataProvider = new RequestDataProvider();
    private final UserRepopository userRepopository;

    public JwtSecurityFilter(AuthenticationManager authenticationManager,
                             UserDetailsService userService,UserRepopository userRepopository) {
        super(authenticationManager);
        this.userService = userService;
        this.userRepopository = userRepopository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        MDC.put("request_unique_id", "[" + requestDataProvider.getRequestHash() + "]");

        String token = request.getHeader("Authorization");
        if (token == null) {
            token = request.getParameter("_authorization");
        }
        if (token == null) {
            logger.debug("token not found in the request");
            System.out.println("token not found in the request");
            chain.doFilter(request, response);
            return;
        }
        try {
            var jwtContent = Jwt.decode(token, globalConfigs.getSecretKey());
            log.debug("jwt token decoded, user = {}", jwtContent.getSubject());
            Optional<User> user = userRepopository.findByEmail(jwtContent.getSubject());
            if (user.isEmpty()) {
                log.error("user not found for id = {}", jwtContent.getSubject());
                System.out.println("user not found for id = " + jwtContent.getSubject());
                throw new UnauthorizeException("user not found");
            }
            // update request hash
//            requestDataProvider.setRequestHash(requestDataProvider.getRequestHash() + "-" +  user.get().getEmail());
//            MDC.put("request_unique_id", "[" + requestDataProvider.getRequestHash() + "]");
            System.out.println(token);
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.get().getEmail(),
                    user.get().getPassword(),
                    new ArrayList<>() // or authorities if you have roles
            );

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception exp) {
            log.error("token decode failed token = {}, error = {}", token, exp.getMessage());
        }
        finally {
            System.out.println("token decode success");
            chain.doFilter(request, response);
        }
    }
}
