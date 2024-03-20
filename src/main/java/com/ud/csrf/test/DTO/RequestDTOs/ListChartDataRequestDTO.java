package com.ud.csrf.test.DTO.RequestDTOs;

import java.util.List;

import lombok.Data;

@Data
public class ListChartDataRequestDTO {

    private List<String> colors;
    private List<ChartDataRequestDTO> series;
    
}
