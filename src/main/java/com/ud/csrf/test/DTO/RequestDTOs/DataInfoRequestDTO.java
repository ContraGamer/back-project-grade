package com.ud.csrf.test.DTO.RequestDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataInfoRequestDTO {

    private String status;
    private Long count;
    private String img;
    private String color;
    
}
