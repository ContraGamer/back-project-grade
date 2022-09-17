package com.ud.csrf.test.Services;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ud.csrf.test.DTO.LogOutResponseDTO;
import com.ud.csrf.test.DTO.LoginDTO;
import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Repository.FinalUserRepository;

@Service
public class FinalUserService {

    @Autowired
    private FinalUserRepository finalUserRepository;

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

    public LogOutResponseDTO logOut(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        LogOutResponseDTO response = new LogOutResponseDTO();
        Enumeration list = httpServletRequest.getHeaderNames();
        ;
        for (int i = 0; list.asIterator().hasNext(); i++) {
            System.out.println(list.nextElement());

        }
        System.out.println(httpServletRequest.getHeader("authorization"));
        for (String i : httpServletRequest.getHeader("authorization").split(" ")) {
            System.out.println("Verify: " + i);
        }

        if (verifierJWT("secret", httpServletRequest.getHeader("authorization").split(" ")[1])) {
            response.setTitle("Usuario ha cerrado sesión correctamente.");
            response.setDescription("");
        } else {
            response.setTitle("Usuario no encontrado, verifiqué la información.");
            response.setDescription("");
        }
        return response;
    }

    public String generateJWT(String secret, String idTypeAndId, FinalUser finalUser) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        String jwt = JWT.create().withSubject(idTypeAndId)
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)).sign(algorithm);
        String tokenUser = finalUser.getTokens() == null ? jwt : finalUser.getTokens() + "," + jwt;
        finalUser.setTokens(tokenUser);
        finalUserRepository.save(finalUser);
        return jwt;
    }

    public boolean verifierJWT(String secret, String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Date dateToken = decodedJWT.getExpiresAt();
        System.out.println(dateToken);
        if (new Date().before(dateToken)) {
            String idType = decodedJWT.getSubject().substring(0,2);
            String id = decodedJWT.getSubject().substring(2, decodedJWT.getSubject().length());
            System.out.println("Tipo: " + idType + " - Id: " + id);
            FinalUser finalUser = finalUserRepository.findByIdTypeAndIdentification(idType, id).get();
            String tokenUser = finalUser.getTokens();
            if (finalUser.getTokens() != null) {
                if (tokenUser.contains(",")) {
                    for (String t : tokenUser.split(",")) {
                        System.out.println("Token1: " + t);
                        System.out.println("Token2: "+token);
                        System.out.println("Token validation: "+t == token);
                        if (t.trim().equals(token.trim()))
                            return true;
                    }
                } else
                    return tokenUser == token;
            } else {
                return false;
            }
        } else {
            // Exception por expiración de tiempo.
        }
        return false;

    }

}
