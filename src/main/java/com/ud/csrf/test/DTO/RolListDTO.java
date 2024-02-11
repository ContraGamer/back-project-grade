package com.ud.csrf.test.DTO;

import java.util.List;

import lombok.Data;

@Data
public class RolListDTO{
    
    private String name;
    private String status;
    private Boolean edit;
    private Boolean delete;
    private Boolean create;
    
}
