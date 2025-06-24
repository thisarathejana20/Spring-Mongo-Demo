package com.personnel.user.demo.utill;

import com.personnel.user.demo.commons.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class JWTAppendAdvice implements ResponseBodyAdvice<Object> {
    GlobalConfig globalConfigs = new GlobalConfig("secret", true);
    UserModuleConfig userModuleConfigs = new UserModuleConfig(1, 1);

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if (Session.getUser() == null) {
            return o;
        }
        JwtContent.JwtContentBuilder content = JwtContent.builder().subject(Session.getUser().getId());
        if (Session.getUser().isAnyAdmin() || Session.getUser().isTenant()) {
            if (globalConfigs.isDebugModeOn()) {
                content.expiredIn(DateUtils.MILLIS_PER_HOUR);
            } else {
                content.expiredIn(DateUtils.MILLIS_PER_HOUR * userModuleConfigs.getTokenExpireTimeWeb());
            }
        } else {
            content.expiredIn(DateUtils.MILLIS_PER_SECOND * userModuleConfigs.getTokenExpireTime());
        }
        if(Session.getUser() != null) {
            content.payload(Map.of("role",Session.getUser().getUserType()));
        }
        String token = Jwt.encode(content.build(), globalConfigs.getSecretKey());
        serverHttpResponse.getHeaders().add("Authorization", token);
        return o;
    }
}
