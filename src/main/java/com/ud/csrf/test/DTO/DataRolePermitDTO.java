package com.ud.csrf.test.DTO;

import java.util.List;

import com.ud.csrf.test.Model.Permit;
import com.ud.csrf.test.Model.Role;

import lombok.Data;

@Data
public class DataRolePermitDTO {

    Role role;
    List<Permit> permits;
    
}
