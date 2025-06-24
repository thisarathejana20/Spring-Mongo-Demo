package com.personnel.user.demo.commons;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;
import com.personnel.user.demo.utill.DateHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Jwt {
    public static String encode(JwtContent jwtContent, String jwtKey) {
        JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        if (jwtContent.getPayload() != null) {
            for (Map.Entry<String, String> entry : jwtContent.getPayload().entrySet()) {
                builder = builder.withClaim(entry.getKey(), entry.getValue());
            }
        }
        String token = builder.withSubject(jwtContent.getSubject())
                .withExpiresAt(DateUtils.addSeconds(DateHelper.nowAsDate(), (int) jwtContent.getExpiredIn()))
                .sign(Algorithm.HMAC256(jwtKey));
        return token;
    }

    public static JwtContent decode(String token, String jwtKey) {
        try {
            JwtContent jwtContent = JwtContent.builder().build();
            Algorithm algorithm = Algorithm.HMAC256(jwtKey);
            JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm).build();
            DecodedJWT content = verifier.verify(token);
            jwtContent.setSubject(content.getSubject());
            Map<String, String> claims = new HashMap<>();
            for (Map.Entry<String, Claim> entry : content.getClaims().entrySet()) {
                claims.put(entry.getKey(), entry.getValue().asString());
            }
            jwtContent.setPayload(claims);
            return jwtContent;
        } catch (JWTVerificationException exp) {
            log.error("cannot decode jwt token, error={}", exp.getMessage());
            throw exp;
        }
    }

    public static JwtContent decodeWithoutSecret(String token) {
        try {
            JwtContent jwtContent = JwtContent.builder().build();
            Payload payload = com.auth0.jwt.JWT.decode(token);
            jwtContent.setSubject(payload.getSubject());
            Map<String, String> claims = new HashMap<>();
            for (Map.Entry<String, Claim> entry : payload.getClaims().entrySet()) {
                claims.put(entry.getKey(), entry.getValue().asString());
            }
            jwtContent.setPayload(claims);
            return jwtContent;
        } catch (JWTVerificationException exp) {
            log.error("cannot decode jwt token, error={}", exp.getMessage());
            return JwtContent.builder().build();
        }
    }
}
