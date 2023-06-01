package com.ud.csrf.test.DTO;

import lombok.Data;

@Data
public class TransferToAcountDTO {

    private String originNumber;
    private String destinyNumber;
    private String cantTranfer;
    
}
