package com.ud.csrf.test.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class MovementsRequestDTO {

    private String name;
    private String typeMov;
    private String originAccount;
    private String destinyAccount;
    private Date date;
    private String value;
    private String code;
    
}
