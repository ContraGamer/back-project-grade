package com.ud.csrf.test.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.csrf.test.DTO.FinalUserResponseDTO;
import com.ud.csrf.test.DTO.LogOutResponseDTO;
import com.ud.csrf.test.DTO.LogUpRequestDTO;
import com.ud.csrf.test.DTO.LogUpResponseDTO;
import com.ud.csrf.test.DTO.LoginDTO;
import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Services.AdditionalsService;
import com.ud.csrf.test.Services.FinalUserService;

@RestController
@RequestMapping("/user")
public class FinalUserController {

    @Autowired
    private FinalUserService finalUserService;

    @Autowired
    private AdditionalsService additionalsService;

    @PostMapping("/login")
    public FinalUserResponseDTO login(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, @RequestBody LoginDTO login) throws Exception{
        FinalUser finalUser = finalUserService.loginService(login);
        String token = additionalsService.generateJWT("secret", login.getIdType()+login.getIdentification(),finalUser);
        httpServletResponse.addHeader("Authorization", token);
        FinalUserResponseDTO dto = new FinalUserResponseDTO();
        dto.setFinalUser(finalUser);
        dto.setAuth(token);
        return dto;
    }

    @PostMapping("/logout")
    public LogOutResponseDTO logOut(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){
        return finalUserService.logOut(httpServletRequest, httpServletResponse);
    }

    @PostMapping("/logup")
    public  LogUpResponseDTO logUp(@RequestBody LogUpRequestDTO logup){
        return finalUserService.logUp(logup);
    }
    
}
