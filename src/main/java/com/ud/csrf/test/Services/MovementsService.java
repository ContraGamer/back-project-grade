package com.ud.csrf.test.Services;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.csrf.test.DTO.MovementsRequestDTO;
import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Model.Movements;
import com.ud.csrf.test.Repository.MovementsRepository;

@Service
public class MovementsService {

    @Autowired
    MovementsRepository movementsRepository;

    @Autowired
    AdditionalsService additionalsService;

    public String insertMovement(final HttpServletRequest httpServletRequest, MovementsRequestDTO movement) {
        FinalUser finalUser = additionalsService.getUserToToken("secret",
                additionalsService.getToken(httpServletRequest));
                //Insertar el registro de salida
        System.out.println(movement.getOriginAccount());
        System.out.println(movement.getDestinyAccount());
        Movements movementSave = new Movements();
        movementSave.setAmount(movement.getValue());
        movementSave.setAcountFirst(movement.getOriginAccount());
        movementSave.setAcountSecond(movement.getDestinyAccount());
        movementSave.setFinalUser(finalUser);
        movementSave.setCode(genereteCodeTx());
        movementSave.setTypeMove(movement.getTypeMov());
        movementSave.setDate(new Date());
        movementsRepository.save(movementSave);
        // //Insertar el registro de entrada
        // Movements movementSave1 = new Movements();
        // movementSave1.setAmount(movement.getValue());
        // movementSave1.setAcountFirst(movement.getDestinyAccount());
        // movementSave1.setAcountSecond(movement.getOriginAccount());
        // movementSave1.setFinalUser(finalUser);
        // movementSave1.setCode(genereteCodeTx());
        // movementSave1.setTypeMove(movement.getTypeMov());
        // movementSave1.setDate(new Date());
        // System.out.println(movementSave1);
        // movementsRepository.save(movementSave1);
        return "Se ha guardado el movimiento satisfactoriamente";
    }

    public String genereteCodeTx() {
        String code = additionalsService.generateRandomNumber(6);
        try {
            if (!movementsRepository.findByCode(code).getCode().equals(code)) {
                return code;
            } else {
                genereteCodeTx();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

}
