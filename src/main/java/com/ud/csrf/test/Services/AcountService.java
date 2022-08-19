package com.ud.csrf.test.Services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.csrf.test.Model.Acount;
import com.ud.csrf.test.Repository.AcountRepository;

@Service
public class AcountService {
    
    @Autowired
    AcountRepository acountRepository;

    /**
     * Tranferir un monto de una cuenta origen a una cuenta destino.
     * @param originNumber
     * @param destinyNumber
     * @param cantTranfer
     * @return
     */
    public String tranferAcountToAcount(String originNumber, String destinyNumber, BigDecimal cantTranfer){
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
}
