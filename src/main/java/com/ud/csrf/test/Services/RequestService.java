package com.ud.csrf.test.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.csrf.test.DTO.RequestDTOs.ChartDataRequestDTO;
import com.ud.csrf.test.DTO.RequestDTOs.ChartDataRequestPieDTO;
import com.ud.csrf.test.DTO.RequestDTOs.ChartDataRequestSubPieDTO;
import com.ud.csrf.test.DTO.RequestDTOs.DataInfoRequestDTO;
import com.ud.csrf.test.DTO.RequestDTOs.ListChartDataRequestDTO;
import com.ud.csrf.test.Repository.RequestRepository;

@Service
public class RequestService {

    @Autowired
    RequestRepository repository;

    private final String FIRSTCOLOR = "#98FB98";
    private final String SECONDCOLOR = "#ADD8E6";
    private final String THIRDCOLOR = "#FFFF99";
    private final String FOURTHCOLOR = "#FF6347";
    private final String STATUS200 = "200";
    private final String STATUS400 = "400";
    private final String STATUS404 = "404";
    private final String STATUS500 = "500";
    private final String TYPECOLUMN = "column";
    private final String HIGHLEVEL = "high";
    private final String MEDIUMLEVEL = "medium";
    private final String WITHOUTLEVEL = "without";

    /**
     * Entrega la informacion para las cards de monitoreo teniendo en cuenta la
     * informacion del peticiones en la tabla request
     * 
     * @return
     */
    public List<DataInfoRequestDTO> getDataInfoRequest() {
        List<DataInfoRequestDTO> list = new ArrayList<DataInfoRequestDTO>();
        DataInfoRequestDTO status200 = new DataInfoRequestDTO(STATUS200, repository.countByStatusHttp(STATUS200),
                "https://img.icons8.com/material-outlined/24/verified-account.png", FIRSTCOLOR);
        list.add(status200);
        DataInfoRequestDTO status400 = new DataInfoRequestDTO(STATUS400, repository.countByStatusHttp(STATUS400),
                "https://img.icons8.com/material-two-tone/24/cancel-2.png", SECONDCOLOR);
        list.add(status400);
        DataInfoRequestDTO status404 = new DataInfoRequestDTO(STATUS404, repository.countByStatusHttp(STATUS404),
                "https://img.icons8.com/ios/50/error--v1.png", THIRDCOLOR);
        list.add(status404);
        DataInfoRequestDTO status500 = new DataInfoRequestDTO(STATUS500, repository.countByStatusHttp(STATUS500),
                "https://img.icons8.com/ios-glyphs/30/bug--v2.png", FOURTHCOLOR);
        list.add(status500);
        return list;
    }

    /**
     * Obtiene la informacion en porcentajes sobre la peticiones, en la
     * listPrincipal se entrega sobre para los status y en la
     * secundaryList se entrega mas detalle del status y del nivel en el que se hizo
     * la solicitud.
     * 
     * @return
     */
    public ChartDataRequestSubPieDTO getDataInfoPorcenRequest() {
        ChartDataRequestSubPieDTO list = new ChartDataRequestSubPieDTO();
        double total = repository.findAll().size();
        List<ChartDataRequestPieDTO> principalList = getDataInfoPorcen(total, true);
        List<ChartDataRequestPieDTO> secundaryList = getDataInfoPorcen(total, false);
        list.setPrincipalData(principalList);
        list.setSecundaryData(secundaryList);
        return list;
    }

    /**
     * Metodo para obtener la informacion en la estructura adecuada para el chart
     * del front en porcentajes.
     * 
     * @param total
     * @param primary
     * @return
     */
    private List<ChartDataRequestPieDTO> getDataInfoPorcen(double total, boolean primary) {
        List<ChartDataRequestPieDTO> list = new ArrayList<ChartDataRequestPieDTO>();
        String[] listStatus = { STATUS200, STATUS400, STATUS404, STATUS500 };
        String[] listLevel = { WITHOUTLEVEL, MEDIUMLEVEL, HIGHLEVEL };
        String[] listColors = { FIRSTCOLOR, SECONDCOLOR, THIRDCOLOR, FOURTHCOLOR };
        int count = 0;
        for (String status : listStatus) {
            ChartDataRequestPieDTO data = new ChartDataRequestPieDTO();
            if (primary) {
                long countStatus = repository.countByStatusHttp(status);
                data.setName("Status " + status);
                data.setY(countStatus / total * 100);
                data.setColor(listColors[count]);
                list.add(data);
            } else {
                for (String level : listLevel) {
                    ChartDataRequestPieDTO dataSecundary = new ChartDataRequestPieDTO();
                    long countStatus = repository.countByStatusHttpAndSecurityLevel(status, level);
                    dataSecundary.setName("Status " + status + " - " + level);
                    dataSecundary.setY(countStatus / total * 100);
                    dataSecundary.setColor(listColors[count]);
                    list.add(dataSecundary);
                }
            }
            count++;
        }
        return list;
    }

    /**
     * Metodo para obtener la informacion del grafico de columnas.
     * 
     * @return
     */
    public ListChartDataRequestDTO getDataToChartColumn() {
        ListChartDataRequestDTO list = new ListChartDataRequestDTO();
        List<String> colors = new ArrayList<String>();
        colors.add(FIRSTCOLOR);
        colors.add(SECONDCOLOR);
        colors.add(FOURTHCOLOR);
        colors.add(THIRDCOLOR);
        list.setColors(colors);
        List<ChartDataRequestDTO> listSeries = new ArrayList<ChartDataRequestDTO>();
        listSeries.add(setDataSeries(WITHOUTLEVEL));
        listSeries.add(setDataSeries(MEDIUMLEVEL));
        listSeries.add(setDataSeries(HIGHLEVEL));
        list.setSeries(listSeries);
        return list;
    }

    /**
     * Metodo que entrega la informacion en la estructura adecuada para el grafico
     * de columnas.
     * 
     * @param levelSecurity
     * @return
     */
    private ChartDataRequestDTO setDataSeries(String levelSecurity) {
        ChartDataRequestDTO series = new ChartDataRequestDTO();
        String[] listStatus = { STATUS200, STATUS400, STATUS404, STATUS500 };
        long[] countStatusArray = { 0, 0, 0, 0 };
        int cont = 0;
        for (String status : listStatus) {
            long countStatus = repository.countByStatusHttpAndSecurityLevel(status, levelSecurity);
            countStatusArray[cont] = countStatus;
            cont++;
        }
        series.setData(countStatusArray);
        series.setName(levelSecurity);
        series.setType(TYPECOLUMN);
        return series;
    }

}
