package com.ud.csrf.test.DTO.RequestDTOs;

import lombok.Data;

@Data
public class ChartDataRequestDTO {

    private String name;
    private long[] data;
    private String type;
    
}
