package com.ud.csrf.test.DTO;

import com.ud.csrf.test.Model.FinalUser;

import lombok.Data;

@Data
public class FinalUserResponseDTO {

    private FinalUser finalUser;
    private String auth;
    
}
