package com.ud.csrf.test.DTO.RequestDTOs;

import java.util.List;

import lombok.Data;

@Data
public class ChartDataRequestSubPieDTO {

    private List<ChartDataRequestPieDTO> principalData;
    private List<ChartDataRequestPieDTO> secundaryData;
    
}
