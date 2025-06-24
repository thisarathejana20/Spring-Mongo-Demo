package com.personnel.user.demo.utill.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
public class UnauthorizeException extends RuntimeException {
    public UnauthorizeException() {
        super("Unauthorized");
    }

    public UnauthorizeException(String message) {
        super(message);
    }

    public UnauthorizeException(String message, Throwable cause) {
        super(message, cause);
    }

    // 👇 Custom method to return JSON string
    public String getJsonAsString(Object additionalData) {
        try {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("status", 401);
            errorMap.put("error", "Unauthorized");
            errorMap.put("message", this.getMessage());
            if (additionalData != null) {
                errorMap.put("data", additionalData);
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(errorMap);
        } catch (Exception e) {
            return "{\"error\": \"Unauthorized\"}";
        }
    }
}
