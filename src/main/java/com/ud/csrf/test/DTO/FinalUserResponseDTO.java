package com.ud.csrf.test.DTO;

import java.util.Date;

import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Model.Role;

import lombok.Data;

@Data
public class FinalUserResponseDTO {

    private FinalUser finalUser;
    private String auth;
    
}
