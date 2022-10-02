package com.ud.csrf.test.Services;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ud.csrf.test.DTO.LogOutResponseDTO;
import com.ud.csrf.test.DTO.LogUpRequestDTO;
import com.ud.csrf.test.DTO.LogUpResponseDTO;
import com.ud.csrf.test.DTO.LoginDTO;
import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Repository.FinalUserRepository;
import com.ud.csrf.test.Repository.RoleRepository;

@Service
public class FinalUserService {

    @Autowired
    private FinalUserRepository finalUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Abre sesion en la cuenta.
     * @param login
     * @return
     * @throws Exception
     */
    public FinalUser loginService(LoginDTO login) throws Exception {
        long l = finalUserRepository.countByIdTypeAndIdentification(login.getIdType(), login.getIdentification());
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
        if (verifierJWT("secret", httpServletRequest.getHeader("authorization").split(" ")[1])) {
            response.setTitle("Usuario ha cerrado sesión correctamente.");
            response.setDescription("");
        } else {
            response.setTitle("Usuario no encontrado, verifiqué la información.");
            response.setDescription("");
        }
        return response;
    }

    /**
     * Genera un JWT al realizar un inicio de sesion
     * 
     * @param secret
     * @param idTypeAndId
     * @param finalUser
     * @return
     */
    public String generateJWT(String secret, String idTypeAndId, FinalUser finalUser) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        String jwt = JWT.create().withSubject(idTypeAndId)
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)).sign(algorithm);
        String tokenUser = finalUser.getTokens() == null ? jwt : finalUser.getTokens() + "," + jwt;
        finalUser.setTokens(tokenUser);
        finalUserRepository.save(finalUser);
        return jwt;
    }

    /**
     * Verifica si un JWT es autentico y si ya caduco
     * 
     * @param secret
     * @param token
     * @return
     */
    public boolean verifierJWT(String secret, String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Date dateToken = decodedJWT.getExpiresAt();
        System.out.println(dateToken);
        if (new Date().before(dateToken)) {
            String idType = decodedJWT.getSubject().substring(0, 2);
            String id = decodedJWT.getSubject().substring(2, decodedJWT.getSubject().length());
            System.out.println("Tipo: " + idType + " - Id: " + id);
            FinalUser finalUser = finalUserRepository.findByIdTypeAndIdentification(idType, id).get();
            String tokenUser = finalUser.getTokens();
            if (finalUser.getTokens() != null) {
                if (tokenUser.contains(",")) {
                    for (String t : tokenUser.split(",")) {
                        if (t.trim().equals(token.trim())) {
                            finalUser.setTokens(finalUser.getTokens().replace(token.trim(), ""));
                            finalUserRepository.save(finalUser);
                            return true;
                        }
                    }
                } else {
                    if (tokenUser == token) {
                        finalUser.setTokens(finalUser.getTokens().replace(token.trim(), ""));
                        finalUserRepository.save(finalUser);
                    }
                    return tokenUser == token;
                }
            } else {
                return false;
            }
        } else {
            // Exception por expiración de tiempo.
        }
        return false;

    }

    /**
     * Registro de usuario
     * 
     * @param logUp
     * @return
     */
    public LogUpResponseDTO logUp(LogUpRequestDTO logUp) {
        FinalUser newUser = new FinalUser();
        newUser.setAuthType("0");
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
        finalUserRepository.save(newUser);
        LogUpResponseDTO response = new LogUpResponseDTO();
        response.setTitle("Se ha registrado exitosamente el usuario");
        response.setSubTitle("Tener en cuenta que tu correo es: " + logUp.getEmail());
        return response;
    }

}
