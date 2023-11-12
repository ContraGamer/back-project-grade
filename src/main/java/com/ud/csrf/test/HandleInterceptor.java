package com.ud.csrf.test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ud.csrf.test.Model.Parameter;
import com.ud.csrf.test.Repository.ParameterRepository;

@Component
public class HandleInterceptor implements HandlerInterceptor {

    @Autowired
    private ParameterRepository parameterRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String exampleHeader = request.getHeader("example-token");
        String csrfHeader = request.getHeader("x-csrf-token");
        if (addExceptionURL(request, "/user/login")) {
            System.out.println("Dato interceptor: " + csrfHeader);            
            return true;
        }
        System.out.println("URL: "+request.getRequestURI()+"; Dato interceptor: " + exampleHeader);
        Boolean dato = csrfSecurity(exampleHeader);
        System.out.println("Dato: " + dato);
        return dato; // Continúa con el procesamiento de la solicitud
        //return true; // Cuando no funciona el filtro.
    }

    private boolean csrfSecurity(String csfrToken) {
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
                System.out.println("Fallo dato csrf: " + csfrToken);
            }

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
    private boolean verifierJWT(String secret, String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Date dateToken = decodedJWT.getExpiresAt();
            return new Date().before(dateToken);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    private boolean addExceptionURL(HttpServletRequest request, String... url) {
        for (String datoString : Arrays.asList(url)) {
            if (request.getRequestURI().equals(datoString)) {
                return true;
            }
        }
        return false;
    }

}
