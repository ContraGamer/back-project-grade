package com.ud.csrf.test.DTO;

import com.ud.csrf.test.Model.TypeAcount;

import lombok.Data;

@Data
public class CreateAcountRequestDTO {

    private String name;
    private TypeAcount typeAcount;
    private String token;
    
}
