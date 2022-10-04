package com.ud.csrf.test.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAcountResponseDTO {
    
    private String message;
    private String subMessage;

}
