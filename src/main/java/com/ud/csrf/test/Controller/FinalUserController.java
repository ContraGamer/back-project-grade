package com.ud.csrf.test.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.csrf.test.DTO.CreateAcountRequestDTO;
import com.ud.csrf.test.DTO.EditEmailRequestDTO;
import com.ud.csrf.test.DTO.FinalUserResponseDTO;
import com.ud.csrf.test.DTO.LogOutResponseDTO;
import com.ud.csrf.test.DTO.LogUpRequestDTO;
import com.ud.csrf.test.DTO.LogUpResponseDTO;
import com.ud.csrf.test.DTO.LoginDTO;
import com.ud.csrf.test.DTO.EditPassRequestDTO;
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
        System.out.println(finalUser);
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

    @PostMapping("/getUserToken")
    public FinalUser getUserToken(final HttpServletRequest httpServletRequest){
        FinalUser user = finalUserService.getUserIntoSession(httpServletRequest);
        return user;
    }
    @PostMapping("/registerUser")
    public LogUpResponseDTO registerUser(final HttpServletRequest httpServletRequest, @RequestBody LogUpRequestDTO request){
        
        return finalUserService.logUp(request);
    }

    @PostMapping("/editpass")
    public LogOutResponseDTO editPass(final HttpServletRequest httpServletRequest, @RequestBody EditPassRequestDTO request){
        
        return finalUserService.editPass(httpServletRequest,request);
    }
    @PostMapping("/editemail")
    public LogOutResponseDTO editemail(final HttpServletRequest httpServletRequest, @RequestBody EditEmailRequestDTO request){

        return finalUserService.editEmail(httpServletRequest,request);
    }
}
