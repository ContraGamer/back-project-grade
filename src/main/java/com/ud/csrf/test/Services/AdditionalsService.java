package com.ud.csrf.test.Services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Model.Parameter;
import com.ud.csrf.test.Repository.FinalUserRepository;
import com.ud.csrf.test.Repository.ParameterRepository;

@Service
public class AdditionalsService {

    @Autowired
    private FinalUserRepository finalUserRepository;

    @Autowired
    private ParameterRepository parameterRepository;

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
        int paramTimeValue = 5;
        try {
            Parameter paramTime = parameterRepository.findByKeyAndState("time-session", "A").get();
            paramTimeValue = Integer.parseInt(paramTime.getValue());
        } catch (Exception e) {
            System.out.println("Error en variable de tiempo de sesión se deja por defecto 5 minutos. ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        String jwt = JWT.create().withSubject(idTypeAndId)
                .withExpiresAt(new Date(System.currentTimeMillis() + paramTimeValue * 60 * 1000)).sign(algorithm);
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
        if (new Date().before(dateToken)) {
            String idType = decodedJWT.getSubject().substring(0, 2);
            String id = decodedJWT.getSubject().substring(2, decodedJWT.getSubject().length());
            FinalUser finalUser = finalUserRepository.findByIdTypeAndIdentification(idType, id).get();
            String tokenUser = finalUser.getTokens();
            if (finalUser.getTokens() != null) {
                if (tokenUser.contains(",")) {
                    for (String t : tokenUser.split(",")) {
                        if (t.trim().equals(token.trim())) {
                            return true;
                        }
                    }
                } else {
                    return tokenUser == token;
                }
            } else {
                return false;
            }
        } else {
            // Exception por expiración de tiempo.
            new Exception("Ha caducado la sesión del usuario.");
        }
        return false;

    }

    /**
     * Obtener usuario por token.
     */
    public FinalUser getUserToToken(String secret, String token){
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        String idType = decodedJWT.getSubject().substring(0, 2);
        String id = decodedJWT.getSubject().substring(2, decodedJWT.getSubject().length());
        FinalUser finalUser = finalUserRepository.findByIdTypeAndIdentification(idType, id).get();
        return finalUser;
    }

    public String generateRandomNumber(int cantidad){
        int min = 0;
        int max = 9;
        String response = "";
        for(int i = 0; i<cantidad; i++){
            int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            response = response + random_int;
        }
        System.out.println("Numero generado: " + response + "\nLogitud de la cadena: " + response.length());
        return response;
    }
    
}
