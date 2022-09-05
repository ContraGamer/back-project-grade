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
import com.ud.csrf.test.DTO.LogOutRequestDTO;
import com.ud.csrf.test.DTO.LoginDTO;
import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Repository.FinalUserRepository;

@Service
public class FinalUserService{

    @Autowired
    private FinalUserRepository finalUserRepository;

    public FinalUser loginService(LoginDTO login) throws Exception{
        long l = finalUserRepository.countByIdTypeAndIdentification(login.getIdType(),login.getIdentification());
        if (l!=1){
            if(l>1){
                throw new Exception("Más de uno usuario.");
            }
            throw new Exception("Usuario no encontrado.");
        }
        FinalUser finalUser = finalUserRepository.findByIdTypeAndIdentificationAndPassword(login.getIdType(),login.getIdentification(), login.getPassword()).get();
        return finalUser;
    }


    public String logOut(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){
        if(verifierJWT("secret",httpServletResponse.getHeader("Authentication").split("Bearer ")[1])){
            
        }
        return null;
    }


    public String generateJWT(String secret, String idTypeAndId, FinalUser finalUser){
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        String jwt = JWT.create().withSubject(idTypeAndId).withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000)).sign(algorithm);
        String tokenUser = finalUser.getTokens() == null ? jwt : ","+jwt;
        finalUser.setTokens(tokenUser);
        finalUserRepository.save(finalUser);
        return jwt;
    }

    public boolean verifierJWT (String secret, String token){
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Date dateToken = decodedJWT.getExpiresAt();
        if(new Date().before(dateToken)){
            String idType = decodedJWT.getSubject().substring(2);
            String id = decodedJWT.getSubject().substring(3,decodedJWT.getSubject().length()-1);
            System.out.println("Tipo: "+idType+" - Id: "+ id);
            FinalUser finalUser = finalUserRepository.findByIdTypeAndIdentification(idType, id).get();
            String tokenUser = finalUser.getTokens();
            if(finalUser.getTokens()!=null){
                if(tokenUser.contains(",")){
                  for (String t : tokenUser.split(",")) {
                    if(t==token)
                        return true;
                  }
                }else
                    return tokenUser == token;
            }else{
                //NO HAY TOKENS DE USUARIO
            }
        }else{
            // Exception por expiración de tiempo.
        }
        return false;
    }

}
