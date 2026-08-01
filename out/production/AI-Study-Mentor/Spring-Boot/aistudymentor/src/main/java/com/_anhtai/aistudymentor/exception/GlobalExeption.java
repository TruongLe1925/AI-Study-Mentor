package com._anhtai.aistudymentor.exception;

import com._anhtai.aistudymentor.dto.reponse.EntityErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExeption {
    // 1. Bắt lỗi Validation cho @RequestBody (MethodArgumentNotValidException)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Lấy danh sách tất cả các field bị lỗi và message tương ứng
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage(); // Message cấu hình ở Annotation (vd: @NotBlank)
            errors.put(fieldName, errorMessage);
        });

        // Trả về HTTP 400 Bad Request kèm Map chi tiết lỗi
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailIsPresentException.class)
    public ResponseEntity<EntityErrorResponse> handleRuntimeException(EmailIsPresentException ex) {
        EntityErrorResponse response = new EntityErrorResponse();
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
