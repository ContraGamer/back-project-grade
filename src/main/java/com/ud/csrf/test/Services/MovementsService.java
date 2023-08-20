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
        Movements movementSave = new Movements();
        movementSave.setAmount(movement.getValue());
        movementSave.setAcountFirst(movement.getOriginAccount());
        movementSave.setAcountSecond(movement.getDestinyAccount());
        movementSave.setFinalUser(finalUser);
        movementSave.setCode(genereteCodeTx());
        movementSave.setTypeMove(movement.getTypeMov());
        movementSave.setDate(new Date());
        movementsRepository.save(movementSave);
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
