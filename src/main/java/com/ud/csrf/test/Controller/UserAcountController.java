package com.ud.csrf.test.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Model.UserAcount;
import com.ud.csrf.test.Repository.UserAcountRepository;
import com.ud.csrf.test.Services.AdditionalsService;

@RestController
@RequestMapping("/user-acount")
public class UserAcountController {

    @Autowired
    private UserAcountRepository userAcountRepository;

    @Autowired
    private AdditionalsService additionalsService;

    @GetMapping("/getAll")
    public List<UserAcount> getAll() {
        return userAcountRepository.findAll();
    }

    @PostMapping("/getUserAccounts")
    public List <UserAcount> getUserAccounts(final HttpServletRequest httpServletRequest, @RequestBody FinalUser finalUser) {
        return userAcountRepository.findByFinalUser(finalUser).get();
    }

    @PostMapping("/getUserAccounts")
    public List<UserAcount> getUserAccounts(final HttpServletRequest httpServletRequest) {
        String token = additionalsService.getToken(httpServletRequest);
        FinalUser finalUser = new FinalUser();
        if(token != "valueNull")
            finalUser = additionalsService.getUserToToken("secret", token);
        System.out.println("Data final user: " + finalUser);
        return userAcountRepository.findByFinalUser(finalUser).get();
    }

}
