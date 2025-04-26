package com.example.loan.exception;

import com.example.loan.model.BaseResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex) {
        BaseResponse<Object> response = new BaseResponse<>(ex.getStatus().value(), ex.getMessage(), null);
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        BaseResponse<Object> response = new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "Validation Failed", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse<Object>>  handleConstraintViolation(ConstraintViolationException ex) {
        BaseResponse<Object> response = new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid Payload", null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Object>> handleUnreadableMessage(HttpMessageNotReadableException ex) {
        BaseResponse<Object> response = new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid Payload", null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>>  handleAll(Exception ex) {
        BaseResponse<Object> response = new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
