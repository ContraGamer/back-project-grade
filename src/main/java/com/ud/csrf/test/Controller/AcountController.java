package com.ud.csrf.test.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ud.csrf.test.DTO.CreateAcountRequestDTO;
import com.ud.csrf.test.DTO.CreateAcountResponseDTO;
import com.ud.csrf.test.Model.Acount;
import com.ud.csrf.test.Repository.AcountRepository;
import com.ud.csrf.test.Repository.UserAcountRepository;
import com.ud.csrf.test.Services.AcountService;

@RestController
@RequestMapping("/acount")
public class AcountController {

    @Autowired
    private AcountRepository acountRepository;

    @Autowired
    private AcountService acountService;

    /**
     * Seguridad extremadamente baja y método de alta criticidad.
     * 
     * @return
     */
    @GetMapping("/getAll")
    public List<Acount> getAll() {
        return acountRepository.findAll();
    }

    /**
     * Seguridad baja
     * 
     * @param idAcount
     * @return
     */
    @PostMapping("/getOneAcount")
    public Acount getOneAcount(@RequestParam long idAcount) {
        return acountRepository.findById(idAcount).get();
    }

    /**
     * Seguridad baja
     * 
     * @param originNumber
     * @param destinyNumber
     * @param cantTranfer
     * @return
     */
    @GetMapping("/transferToAcount")
    public String transferToAcount(@RequestParam String originNumber, @RequestParam String destinyNumber,
            @RequestParam String cantTranfer) {
        String response = acountService.tranferAcountToAcount(originNumber, destinyNumber, new BigDecimal(cantTranfer));
        return response;
    }

    /**
     * Seguridad baja
     * 
     * @param acountNumber
     * @param cantTranfer
     * @return
     */
    @PostMapping("/tranferCashToAcount")
    public String transferCashToAcount(@RequestParam String acountNumber, @RequestParam String cantTranfer) {
        String response = acountService.addAmountToAcount(acountNumber, new BigDecimal(cantTranfer));
        return response;
    }

    /**
     * Seguridad media, solo el usuario o el JWT del usuario puede crear cuentas.
     * @param request
     * @return
     */
    @PostMapping("/createAcount")
    public CreateAcountResponseDTO createAcount(CreateAcountRequestDTO request){
        return acountService.createAcount(request);
    }

}
