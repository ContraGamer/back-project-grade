package com.ud.csrf.test.Services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Repository.FinalUserRepository;

@Service
public class AdditionalsService {

    @Autowired
    private FinalUserRepository finalUserRepository;

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
    
}
