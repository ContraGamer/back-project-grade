package com.ud.csrf.test.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ud.csrf.test.DTO.CreateAcountRequestDTO;
import com.ud.csrf.test.DTO.CreateAcountResponseDTO;
import com.ud.csrf.test.DTO.GenericResponseDTO;
import com.ud.csrf.test.DTO.TransferToAcountDTO;
import com.ud.csrf.test.Model.Acount;
import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Model.UserAcount;
import com.ud.csrf.test.Repository.AcountRepository;
import com.ud.csrf.test.Repository.UserAcountRepository;
import com.ud.csrf.test.Services.AcountService;
import com.ud.csrf.test.Services.AdditionalsService;

@RestController
@RequestMapping("/acount")
public class AcountController {

    @Autowired
    private AcountRepository acountRepository;

    @Autowired
    private AcountService acountService;

    @Autowired
    private AdditionalsService additionalsService;

    @Autowired
    private UserAcountRepository userAcountRepository;

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
    @PostMapping("/transferToAcount")
    public GenericResponseDTO<String> transferToAcount(final HttpServletRequest httpServletRequest, @RequestParam String originNumber, @RequestParam String destinyNumber,
            @RequestParam String cantTranfer) {
        GenericResponseDTO<String> response = acountService.tranferAcountToAcount(httpServletRequest, originNumber, destinyNumber, new BigDecimal(cantTranfer));
        return additionalsService.responseController(response.getData(), response.getStatus());
    }

    /**
     * Seguridad baja
     * 
     * @param originNumber
     * @param destinyNumber
     * @param cantTranfer
     * @return
     */
    @PostMapping("/transferToAcount2")
    public GenericResponseDTO<String> transferToAcount2(final HttpServletRequest httpServletRequest, @RequestBody TransferToAcountDTO transferToAcount) {
        System.out.println("transferToAcount: " + transferToAcount);
        GenericResponseDTO<String> response = acountService.tranferAcountToAcount(httpServletRequest, transferToAcount.getOriginNumber(), transferToAcount.getDestinyNumber(),
        new BigDecimal(transferToAcount.getCantTranfer()));
        return additionalsService.responseController(response.getData(), response.getStatus(),response.getMessage(),response.getSubMessage());
    }

    /**
     * Seguridad baja
     * 
     * @param originNumber
     * @param destinyNumber
     * @param cantTranfer
     * @return
     */
    @PostMapping("/transferToAcount3")
    public GenericResponseDTO<String> transferToAcount3(final HttpServletRequest httpServletRequest, @RequestParam String originNumber, @RequestParam String destinyNumber, @RequestParam String cantTranfer) {
        System.out.println("originNumber: " + originNumber + " ; destinyNumber: " + destinyNumber + " ; cantTranfer : " + cantTranfer );
        GenericResponseDTO<String> response = acountService.tranferAcountToAcount(httpServletRequest, originNumber, destinyNumber,
        new BigDecimal(cantTranfer));
        return additionalsService.responseController(response.getData(), response.getStatus());
    }

    /**
     * Seguridad baja
     * 
     * @param acountNumber
     * @param cantTranfer
     * @return
     */
    @PostMapping("/tranferCashToAcount")
    public GenericResponseDTO<String> transferCashToAcount(@RequestParam String acountNumber, @RequestParam String cantTranfer) {
        String response = acountService.addAmountToAcount(acountNumber, new BigDecimal(cantTranfer));
        return additionalsService.responseController(response, 0);
    }

    /**
     * Seguridad media, solo el usuario o el JWT del usuario puede crear cuentas.
     * @param request
     * @return
     */
    @PostMapping("/createAcount")
    public CreateAcountResponseDTO createAcount(final HttpServletRequest httpServletRequest, @RequestBody CreateAcountRequestDTO request){
        String token = httpServletRequest.getHeader("authorization").split(" ")[1];
        if(additionalsService.verifierJWT("secret", token)){
            request.setToken(httpServletRequest.getHeader("authorization").split(" ")[1]);
        }
        return acountService.createAcount(request);
    }

    @PostMapping("/getAccountsWithToken")
    public List<Acount> getAccountsWithToken(final HttpServletRequest httpServletRequest) {
        String token = additionalsService.getToken(httpServletRequest);
        FinalUser finalUser = new FinalUser();
        if(token != "valueNull")
            finalUser = additionalsService.getUserToToken("secret", token);
        System.out.println("Data final user: " + finalUser);
        List<Acount> listAcount = new ArrayList();
        for (UserAcount acount : userAcountRepository.findByFinalUser(finalUser).get()) {
            listAcount.add(acount.getAcount());
        }
        return listAcount;
    }

}
