package com.ud.csrf.test.DTO;

import lombok.Data;

@Data
public class GenericRequestDTO<T> {
    private int state;
    private String message;
    private T data;
}
