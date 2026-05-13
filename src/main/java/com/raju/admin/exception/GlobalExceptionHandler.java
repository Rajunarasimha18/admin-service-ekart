package com.raju.admin.exception;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(
            DuplicateRecordException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    
    // 🔥 Feign exception handler (THIS FIXES TRACE)
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, Object>> handleFeignException(
            FeignException ex) {

        HttpStatus status = HttpStatus.valueOf(ex.status());

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", ex.status());

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> feignBody =
                    mapper.readValue(ex.contentUTF8(), Map.class);

            response.put("message",
                    feignBody.getOrDefault("message", "Request failed"));
        } catch (Exception e) {
            response.put("message", "Request failed");
        }

        return new ResponseEntity<>(response, status);
    }
    
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<?> handleCommonException(CommonException ex) {

        return ResponseEntity
                .status(ex.getHttpStatus())   // ✅ use your status (422)
                .body(Map.of(
                    "message", ex.getMessage(),
                    "status", ex.getHttpStatus().value()
                ));
    }
}