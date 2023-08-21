package com.ud.csrf.test.Services;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.csrf.test.DTO.CreateAcountRequestDTO;
import com.ud.csrf.test.DTO.CreateAcountResponseDTO;
import com.ud.csrf.test.DTO.MovementsRequestDTO;
import com.ud.csrf.test.Model.Acount;
import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Repository.AcountRepository;
import com.ud.csrf.test.Repository.UserAcountRepository;

@Service
public class AcountService {
    
    @Autowired
    AcountRepository acountRepository;

    @Autowired
    AdditionalsService additionalsService;

    @Autowired
    UserAcountService userAcountService;

    @Autowired
    UserAcountRepository userAcountRepository;

    @Autowired
    MovementsService movementsService;

    /**
     * Tranferir un monto de una cuenta origen a una cuenta destino.
     * @param originNumber
     * @param destinyNumber
     * @param cantTranfer
     * @return
     */
    public String tranferAcountToAcount(final HttpServletRequest httpServletRequest, String originNumber, String destinyNumber, BigDecimal cantTranfer){
        Acount origin = acountRepository.findByNumber(originNumber).get();
        Acount destiny = acountRepository.findByNumber(destinyNumber).get();
        BigDecimal originAmount = new BigDecimal(origin.getAmount()).add(cantTranfer.negate());
        if(originAmount.compareTo(cantTranfer)==1){
            BigDecimal destinyAmount = new BigDecimal(destiny.getAmount()).add(cantTranfer);
            origin.setAmount(originAmount+"");
            destiny.setAmount(destinyAmount+"");
            acountRepository.save(origin);
            acountRepository.save(destiny);
            System.out.println("Transacción exitosa entre la cuenta: "+ originNumber + " a la cuenta: " + destinyNumber);
            movementsService.insertMovement(httpServletRequest, setDataInsertMovement(originNumber, destinyNumber, cantTranfer.toString()));
            return "Transacción exitosa entre la cuenta: "+ originNumber + " a la cuenta: " + destinyNumber;
        }else{
            System.out.println("Transacción fallida la cuenta " + originNumber + " no cuenta con saldo suficiente ");
            return "Transacción fallida la cuenta " + originNumber + " no cuenta con saldo suficiente ";
        }

    }
    
    /**
     * Recargar una cuenta
     * @param acountNumber
     * @param cantTranfer
     * @return
     */
    public String addAmountToAcount(String acountNumber, BigDecimal cantTranfer){
        Acount acount = acountRepository.findByNumber(acountNumber).get();
        BigDecimal amount = new BigDecimal(acount.getAmount()).add(cantTranfer);
        acount.setAmount(amount+"");
        acountRepository.save(acount);
        return "Recarga de cuenta existosa";
    }

    /**
     * Crear una cuenta.
     * @param request
     * @return
     */
    public CreateAcountResponseDTO createAcount(CreateAcountRequestDTO request){
        Acount newAcount =  new Acount();
        newAcount.setAmount("0");
        String name = request.getName() == null || request.getName().trim() == "" ? request.getTypeAcount().getName() : request.getName();
        newAcount.setName(name);
        int cantidad = request.getTypeAcount().getName().equals("DÉBITO") ? 16 : 12 ;  
        newAcount.setNumber(additionalsService.generateRandomNumber(cantidad));
        newAcount.setTypeAcount(request.getTypeAcount());
        acountRepository.save(newAcount);
        FinalUser finalUser = additionalsService.getUserToToken("secret", request.getToken());
        userAcountService.createAcountToUser(finalUser, newAcount);
        return new CreateAcountResponseDTO("Se ha creado su cuenta de tipo " + request.getTypeAcount().getName(), "Recuerde personalizar su cuenta dentro de las opciones de la página.");
    }

    private MovementsRequestDTO setDataInsertMovement(String origin, String destiny, String value) {
        MovementsRequestDTO movement = new MovementsRequestDTO();
        movement.setOriginAccount(origin);
        movement.setDestinyAccount(destiny);
        movement.setTypeMov("1");
        movement.setValue(value);
        return movement;
    }
}
