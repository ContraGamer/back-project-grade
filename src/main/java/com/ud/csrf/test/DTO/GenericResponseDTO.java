package com.ud.csrf.test.DTO;

import lombok.Data;

@Data
public class GenericResponseDTO<T>{
    
    private String message;
    private String subMessage;
    private int status;
    private T data;

}
