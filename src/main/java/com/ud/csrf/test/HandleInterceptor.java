package com.ud.csrf.test;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ud.csrf.test.Model.Parameter;
import com.ud.csrf.test.Repository.ParameterRepository;
import com.ud.csrf.test.Services.AdditionalsService;

@Component
public class HandleInterceptor implements HandlerInterceptor {

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private AdditionalsService additionalsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String csrfHeader = request.getHeader("X-CSRF-TOKEN");
        if(addExceptionURL(request, "/user/login")){
            return true;
        }
        System.out.println("Dato interceptor: " + csrfHeader);
        Boolean data = csrfSecurity(csrfHeader);
        System.out.println("Dato result: " + data);
        return data; // Continúa con el procesamiento de la solicitud
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
                        return additionalsService.verifierJWT("secret", csfrToken);
                    default:
                        return false;
                }
            } catch (Exception e) {
                System.out.println("Fallo dato csrf: " + csfrToken);
            }

        }
        return false;
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
