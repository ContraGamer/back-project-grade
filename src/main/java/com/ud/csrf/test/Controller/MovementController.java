package com.ud.csrf.test.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.csrf.test.DTO.GenericRequestDTO;
import com.ud.csrf.test.DTO.GenericResponseDTO;
import com.ud.csrf.test.DTO.MovementsRequestDTO;
import com.ud.csrf.test.DTO.MovementsResponseDTO;
import com.ud.csrf.test.Model.Movements;
import com.ud.csrf.test.Repository.MovementsRepository;
import com.ud.csrf.test.Services.MovementsService;

@RestController
@RequestMapping("/movements")
public class MovementController {

    @Autowired
    MovementsRepository movementsRepository;

    @Autowired
    MovementsService movementsService;

    @PostMapping("/getMovementsByAccount")
    public List<MovementsResponseDTO> getMovementsByAccount(final HttpServletRequest httpServletRequest,  @RequestBody GenericRequestDTO<String> originAccount){
        List<Movements> list = movementsRepository.findByAcountFirst(originAccount.getData());
        List<MovementsResponseDTO> listResp = new ArrayList<>();
        for (Movements movement : list){
            MovementsResponseDTO dato = new MovementsResponseDTO();
            dato.setOriginAccount(movement.getAcountFirst());
            dato.setDestinyAccount(movement.getAcountSecond());
            dato.setTypeMov(movement.getTypeMove());
            dato.setDate(movement.getDate());
            dato.setValue(movement.getAmount());
            dato.setCode(movement.getCode());
            listResp.add(dato);
            System.out.println(list.size() + "Data origin: "+ movement.getAcountFirst());
        }
        return listResp;
    }

    @PostMapping("/insertMovement")
    public GenericResponseDTO<String> insertMovement(final HttpServletRequest httpServletRequest,  @RequestBody MovementsRequestDTO movement){
        movementsService.insertMovement(httpServletRequest, movement);
        GenericResponseDTO<String> response = new GenericResponseDTO<String>();
        response.setData("Se ha agregado con exito el movimiento");
        response.setMessage("Se ha agregado con exito el movimiento");
        response.setStatus(0);
        return response;
    }
    
}
