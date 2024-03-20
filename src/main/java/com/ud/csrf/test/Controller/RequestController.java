package com.ud.csrf.test.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.csrf.test.DTO.RequestDTOs.ChartDataRequestSubPieDTO;
import com.ud.csrf.test.DTO.RequestDTOs.DataInfoRequestDTO;
import com.ud.csrf.test.DTO.RequestDTOs.ListChartDataRequestDTO;
import com.ud.csrf.test.Model.Request;
import com.ud.csrf.test.Repository.RequestRepository;
import com.ud.csrf.test.Services.RequestService;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestService requestService;

    /**
     * Entrega la informacion de la tabla de todos lo que hay en la base de datos.
     * 
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/getDataRequests")
    public List<Request> getUserToken(final HttpServletRequest httpServletRequest) {
        return requestRepository.findAll();
    }

    /**
     * Entrega la infromacion de las cards de monitoreo.
     * 
     * @return
     */
    @GetMapping("/getDataInfoRequest")
    public List<DataInfoRequestDTO> getDataInfoRequest() {
        return requestService.getDataInfoRequest();
    }

    /**
     * Entrega la informacion para mostrar la grafica de columnas.
     * 
     * @return
     */
    @GetMapping("/getDataToChartColumn")
    public ListChartDataRequestDTO getDataToChartColumn() {
        return requestService.getDataToChartColumn();
    }

    /**
     * Entrega la informacion necesario para el grafico circular.
     * 
     * @return
     */
    @GetMapping("/getDataInfoPorcenRequest")
    public ChartDataRequestSubPieDTO getDataInfoPorcenRequest() {
        return requestService.getDataInfoPorcenRequest();
    }

}
