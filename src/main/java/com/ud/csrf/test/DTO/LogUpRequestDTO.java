package com.ud.csrf.test.DTO;

import lombok.Data;

@Data
public class LogUpRequestDTO {
    
    private String name;
    private String idType;
    private String identification;
    private String password;
    private String email;
    private String cellphone;
    private String address;
    
}
