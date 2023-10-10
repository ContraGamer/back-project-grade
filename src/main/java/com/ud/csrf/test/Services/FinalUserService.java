package com.ud.csrf.test.Services;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.csrf.test.DTO.LogOutResponseDTO;
import com.ud.csrf.test.DTO.LogUpRequestDTO;
import com.ud.csrf.test.DTO.LogUpResponseDTO;
import com.ud.csrf.test.DTO.LoginDTO;
import com.ud.csrf.test.DTO.EditPassRequestDTO;
import com.ud.csrf.test.DTO.EditEmailRequestDTO;
import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Repository.FinalUserRepository;
import com.ud.csrf.test.Repository.RoleRepository;

@Service
public class FinalUserService {

    @Autowired
    private FinalUserRepository finalUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdditionalsService additionalsService;

    /**
     * Abre sesion en la cuenta.
     * @param login
     * @return
     * @throws Exception
     */
    public FinalUser loginService(LoginDTO login) throws Exception {
        long l = finalUserRepository.countByIdTypeAndIdentification(login.getIdType(), login.getIdentification());
        System.out.println(login);
        System.out.println(l);
        if (l != 1) {
            if (l > 1) {
                throw new Exception("Más de uno usuario.");
            }
            throw new Exception("Usuario no encontrado.");
        }
        FinalUser finalUser = finalUserRepository.findByIdTypeAndIdentificationAndPassword(login.getIdType(),
                login.getIdentification(), login.getPassword()).get();
        return finalUser;
    }

    /**
     * Cierra session al eliminar el JWT del usuario.
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    public LogOutResponseDTO logOut(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        LogOutResponseDTO response = new LogOutResponseDTO();
        String token = httpServletRequest.getHeader("authorization").split(" ")[1];
        if (additionalsService.verifierJWT("secret", token)) {
            FinalUser finalUser = additionalsService.getUserToToken("secret", token);
            finalUser.setTokens(finalUser.getTokens().replace(token.trim(), ""));
            finalUserRepository.save(finalUser);
            response.setTitle("Usuario ha cerrado sesión correctamente.");
            response.setDescription("");
        } else {
            response.setTitle("Usuario no encontrado, verifiqué la información.");
            response.setDescription("");
        }
        return response;
    }

    /**
     * Registro de usuario
     * 
     * @param logUp
     * @return
     */
    public LogUpResponseDTO logUp(LogUpRequestDTO logUp) {
        System.out.println(logUp);
        FinalUser newUser = new FinalUser();
        newUser.setAuthType("A");
        newUser.setCellphone(logUp.getCellphone());
        newUser.setCreationDate(new Date());
        newUser.setEmail(logUp.getEmail());
        newUser.setIdType("01");
        newUser.setIdentification(logUp.getIdentification());
        newUser.setName(logUp.getName());
        newUser.setPassword(logUp.getPassword());
        newUser.setRole(roleRepository.findByName("Cliente").get());
        newUser.setState("A");
        newUser.setAddress(logUp.getAddress());
        System.out.println(newUser);
        finalUserRepository.save(newUser);
        LogUpResponseDTO response = new LogUpResponseDTO();
        response.setTitle("Se ha registrado exitosamente el usuario");
        response.setSubTitle("Tener en cuenta que tu correo es: " + logUp.getEmail());
        return response;
    }

    /**
     * Obtiene el usuario que esta en session por medio del token JWT.
     * @param httpServletRequest
     * @return
     */
    public FinalUser getUserIntoSession(final HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("authorization").split(" ")[1];
        if(additionalsService.verifierJWT("secret", token)){
           return additionalsService.getUserToToken("secret", token);
        }
        return new FinalUser();
    }

    //Editar password
    public LogOutResponseDTO editPass(final HttpServletRequest httpServletRequest,EditPassRequestDTO editPass){
        LogOutResponseDTO response = new LogOutResponseDTO();
        String token = httpServletRequest.getHeader("authorization").split(" ")[1];
        if (additionalsService.verifierJWT("secret", token)) {
            FinalUser finalUser = additionalsService.getUserToToken("secret", token);
            finalUser.setPassword(editPass.getPassword());
            finalUserRepository.save(finalUser);
            response.setTitle("Se realizo el cambio correctamente");
            response.setDescription("Usuario actualizado correctamente");
        } else {
            response.setTitle("Usuario no encontrado, verifiqué la información.");
            response.setDescription("");
        }
        return response;

    }
    //Editar email
    public LogOutResponseDTO editEmail(final HttpServletRequest httpServletRequest,EditEmailRequestDTO editEmail){
        LogOutResponseDTO response = new LogOutResponseDTO();
        String token = httpServletRequest.getHeader("authorization").split(" ")[1];
        if (additionalsService.verifierJWT("secret", token)) {
            FinalUser finalUser = additionalsService.getUserToToken("secret", token);
            finalUser.setPassword(editEmail.getPassword());
            finalUser.setEmail(editEmail.getEmail());
            finalUserRepository.save(finalUser);
            response.setTitle("Se realizo el cambio correctamente");
            response.setDescription("Usuario actualizado correctamente");
        } else {
            response.setTitle("Usuario no encontrado, verifiqué la información.");
            response.setDescription("");
        }
        return response;

    }

    


}
