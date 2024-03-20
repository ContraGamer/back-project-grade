package com.ud.csrf.test.Services;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ud.csrf.test.DTO.GenericResponseDTO;
import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Model.Parameter;
import com.ud.csrf.test.Model.Request;
import com.ud.csrf.test.Repository.FinalUserRepository;
import com.ud.csrf.test.Repository.ParameterRepository;
import com.ud.csrf.test.Repository.RequestRepository;

@Service
public class AdditionalsService {

    @Autowired
    private FinalUserRepository finalUserRepository;

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private RequestRepository requestRepository;

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
            System.out.println(
                    "Error en variable de tiempo de sesión se deja por defecto 5 minutos. ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        String jwt = JWT.create().withSubject(idTypeAndId)
                .withExpiresAt(new Date(System.currentTimeMillis() + paramTimeValue * 60 * 1000)).sign(algorithm);
        String tokenUser = finalUser.getTokens() == null || finalUser.getTokens() == "" ? jwt : finalUser.getTokens() + "," + jwt;
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
        try {
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
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return false;

    }

    /**
     * Verifica si un JWT es autentico y si ya caduco
     * 
     * @param secret
     * @param token
     * @return
     */
    public boolean verifierJWT(String secret, String token, boolean valideExp) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
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
                if (valideExp) {
                    return false;
                }
                // Exception por expiración de tiempo.
                new Exception("Ha caducado la sesión del usuario.");
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return false;

    }

    /**
     * Obtener usuario por token.
     */
    public FinalUser getUserToToken(String secret, String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        String idType = decodedJWT.getSubject().substring(0, 2);
        String id = decodedJWT.getSubject().substring(2, decodedJWT.getSubject().length());
        FinalUser finalUser = finalUserRepository.findByIdTypeAndIdentification(idType, id).get();
        return finalUser;
    }

    public String generateRandomNumber(int cantidad) {
        int min = 0;
        int max = 9;
        String response = "";
        for (int i = 0; i < cantidad; i++) {
            int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
            response = response + random_int;
        }
        System.out.println("Numero generado: " + response + "\nLogitud de la cadena: " + response.length());
        return response;
    }

    public String getToken(final HttpServletRequest httpServletRequest) {
        try {
            if (null != httpServletRequest.getHeader("authorization")) {
                return httpServletRequest.getHeader("authorization").split(" ")[1];
            } else if (null != httpServletRequest.getHeader("cookie")) {
                return (httpServletRequest.getHeader("cookie")+"").split("token=")[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "valueNull";
        }
        return null;
    }

    public String getHeaderCSRF(final HttpServletRequest httpServletRequest) {
        try {
            return httpServletRequest.getHeader("X-CSRF-TOKEN");
        } catch (Exception e) {
            e.printStackTrace();
            return "valueNull";
        }
    }

    public <T> GenericResponseDTO<T> responseController(T Data, int status, String msg, String subMsg) {
        GenericResponseDTO<T> response = new GenericResponseDTO<>();
        response.setStatus(status);
        response.setMessage(msg);
        response.setSubMessage(subMsg);
        response.setData(Data);
        return response;
    }

    public <T> GenericResponseDTO<T> responseController(T Data, int status, String msg) {
        GenericResponseDTO<T> response = new GenericResponseDTO<>();
        response.setStatus(status);
        response.setMessage(msg);
        response.setData(Data);
        return response;
    }

    public <T> GenericResponseDTO<T> responseController(T Data, int status) {
        GenericResponseDTO<T> response = new GenericResponseDTO<>();
        response.setStatus(status);
        response.setData(Data);
        return response;
    }

    public boolean csrfSecurity(String csfrToken) {
        final String CEROSEGURITY = "level-attack-without-security";
        final String MEDIUMSECURITY = "level-attack-medium-security";
        final String HIGHSECURITY = "level-attack-high-security";
        List<Parameter> parameterList = parameterRepository.findByOfAndState("SEGURITY", "A").get();
        for (Parameter parameter : parameterList) {
            try {
                switch (parameter.getKey()) {
                    case CEROSEGURITY:
                        System.out.println("No requiere validacion de token");
                        return true;
                    case MEDIUMSECURITY:
                        System.out.println("Requiere validacion de token estatico");
                        return (csfrToken.equals("your-csrf-token-value"));
                    case HIGHSECURITY:
                        System.out.println("Requiere validacion de token dinamico");
                        return verifierJWT("secret", csfrToken);
                    default:
                        return false;
                }
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("Dato csrf: " + csfrToken);
            }

        }
        return false;
    }

    /**
     * Funcion para validar tokens 1 veces al dia y vacias la DB de los tokens expirados
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void myScheduledMethod() {
        for (FinalUser user : finalUserRepository.findAll()) {
            System.out.println(user.getName());
            String newTokens = "";
            String tokens = user.getTokens();
            try {
                int cont = 0;
                if (tokens != null) {
                    if (tokens.contains(",")) {
                        for (String t : user.getTokens().split(",")) {
                            if (verifierJWT("secret", t, true)) {
                                cont++;
                                System.out.println("t"+ t);
                                newTokens = newTokens.concat(t).concat(",");
                            }
                        }
                    } else {
                        if (verifierJWT("secret", tokens, true)) {
                            cont++;
                            newTokens = newTokens.concat(tokens).concat(",");
                        }
                    }
                    user.setTokens(newTokens);
                    finalUserRepository.save(user);
                }
                System.out.println("Numero de tokens recueperado" + cont);
            } catch (Exception e) {
                System.out.println("Error borrando");
                System.out.println(e);
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteDataRequst() {
        int cantTotal = 0;
        try {
            String cantTotalS = parameterRepository.findByKeyAndState("cant-request", "A").get().getValue();
            cantTotal = Integer.parseInt(cantTotalS);
            System.out.println("Cantidad de registros permitidos por parametros: " + cantTotal);
        } catch (Exception e) {
            cantTotal = 500;
        }
        List<Request> listReq = requestRepository.findByOrderByDateTimeStartDesc();
        int cont = 0;
        for (Request request : listReq) {
            if(cont >= cantTotal){
                requestRepository.delete(request);
            }
            cont++;
        }
    }

}
