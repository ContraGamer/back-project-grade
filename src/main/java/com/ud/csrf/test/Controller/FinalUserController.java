package com.ud.csrf.test.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.csrf.test.DTO.LogOutRequestDTO;
import com.ud.csrf.test.DTO.LogOutResponseDTO;
import com.ud.csrf.test.DTO.LoginDTO;
import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Services.FinalUserService;

@RestController
@RequestMapping("/user")
public class FinalUserController {

    @Autowired
    FinalUserService finalUserService;

    @PostMapping("/login")
    public FinalUser login(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, @RequestBody LoginDTO login) throws Exception{
        FinalUser finalUser = finalUserService.loginService(login);
        httpServletResponse.addHeader("Authorization", "Bearer " + finalUserService.generateJWT("secret", login.getIdType()+login.getIdentification(),finalUser));
        return finalUser;
    }

    @PostMapping("/logout")
    public LogOutResponseDTO logOut(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, LogOutRequestDTO logOutRequestDTO){
        return null;
    }
    
}
