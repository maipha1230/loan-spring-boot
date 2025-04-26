package com.example.loan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    public Integer statusCode;
    public String message;
    public T data;

    public BaseResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
