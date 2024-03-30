package com.ud.csrf.test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ud.csrf.test.Model.Parameter;
import com.ud.csrf.test.Model.Request;
import com.ud.csrf.test.Repository.ParameterRepository;
import com.ud.csrf.test.Repository.RequestRepository;

@Component
public class HandleInterceptor implements HandlerInterceptor {

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired 
    private RequestRepository requestRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("------------ Interceptor inicio -------------");
        Instant inicio = Instant.now();
        java.sql.Timestamp timestamp = java.sql.Timestamp.from(inicio);
        request.setAttribute("info-start-time", timestamp); 
        System.out.println(request.getRequestURI() + inicio);
        String csrfHeader = request.getHeader("x-csrf-token");
        if (addExceptionURL(request, "/user/login", "/params/securityParamsActive","/user/registerUser")) {
            System.out.println("Url: ".concat(request.getRequestURI()));    
            request.setAttribute("info-csrf", "Public - authorized");     
            return true;
        }
        Boolean dato = csrfSecurity(csrfHeader); 
        request.setAttribute("info-csrf", "Private - " + (dato ? "authorized": "unauthorized"));  
        if(!dato){
            saveDataRequest(request, response);
        }
        return dato; // Continúa con el procesamiento de la solicitud
        //return true; // Cuando no funciona el filtro.
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
                saveDataRequest(request, response);
                System.out.println("------------ Fin de interceptor -------------");  
    }

    private void saveDataRequest(HttpServletRequest request, HttpServletResponse response) {
        if(!request.getRequestURI().contains("request")){ //Evita que se guarden registros para las peticiones de monitoreo
            Request requestData = new Request();
            requestData.setDataRequest(request.getMethod());
            requestData.setDestiny(request.getRequestURI());
            requestData.setOrigin(getOriginRequest(request));
            requestData.setDateTimeStart((Date) request.getAttribute("info-start-time"));
            Date timeFinish = java.sql.Timestamp.from(Instant.now());
            requestData.setDateTimeFinish(timeFinish);
            requestData.setTimeTaken((timeFinish.getTime() - ((Date) request.getAttribute("info-start-time")).getTime())+"");
            requestData.setStatusHttp(""+response.getStatus());
            requestData.setSecurityLevel((parameterRepository.findByOfAndState("SEGURITY", "A").get().get(0)).getAdditional());
            requestData.setStatusRequest(request.getAttribute("info-csrf")+"");
            requestRepository.save(requestData);
        }
    }

    private String getOriginRequest(HttpServletRequest request) {
        Enumeration<String> nombresDeHeaders = request.getHeaderNames();
        
        // Iterar sobre la enumeración para obtener y mostrar los valores de los headers
        while (nombresDeHeaders.hasMoreElements()) {
            String nombreDelHeader = nombresDeHeaders.nextElement();
            String valorDelHeader = request.getHeader(nombreDelHeader);
            // System.out.println("Header: " + nombreDelHeader + ", Valor: " + valorDelHeader);
            if(nombreDelHeader.equals("origin")){
                return valorDelHeader;
            }

        }
        return "";
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
