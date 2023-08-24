package com.ud.csrf.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ud.csrf.test.Model.Parameter;
import com.ud.csrf.test.Repository.ParameterRepository;

@Configuration
public class Configurations {

    @Autowired
    ParameterRepository parameterRepository;

    String CEROSEGURITY = "level-attack-without-security";
    String MEDIUMSECURITY = "level-attack-medium-security";
    String HIGHSECURITY = "level-attack-high-security";
    
    @Bean
    public WebMvcConfigurer CORSConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                List<Parameter> parameterList = parameterRepository.findByOfAndState("SEGURITY", "A").get();
                for (Parameter parameter : parameterList) {
                    System.out.println("Dato: " + parameter.getKey());
                    if(parameter.getKey().equals(CEROSEGURITY)){
                        System.out.println("SEGURIDAD CERO");
                        ceroSecurity(registry);
                    }
                    else if(parameter.getKey().equals(MEDIUMSECURITY)){
                        System.out.println("SEGURIDAD MEDIA");
                        mediumSecurity(registry);
                    }
                    else if(parameter.getKey().equals(HIGHSECURITY)){
                        System.out.println("SEGURIDAD ALTA");
                        highSecurity(registry);
                    } else {
                        System.out.println("SEGURIDAD POR DEFECTO");
                        registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT","DELETE").allowCredentials(false).allowedHeaders("*");
                    }
                }
            }

            public void ceroSecurity(CorsRegistry registry) {
                System.out.println("SEGURIDAD CERO");
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT","DELETE").allowCredentials(false).allowedHeaders("*");
            }

            public void mediumSecurity(CorsRegistry registry) {
                System.out.println("SEGURIDAD MEDIA");
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT","DELETE");
            }
                        
            public void highSecurity(CorsRegistry registry) {
                System.out.println("SEGURIDAD ALTA");
                System.out.println("");
                registry.addMapping("/**")
                        .allowedOrigins("http://*", "https://*", "http://localhost:4200", "https://bank-sim-project.firebaseapp.com", "https://bank-sim-project.web.app")
                        .allowedMethods("GET", "POST", "PUT","DELETE").allowCredentials(true).allowedHeaders("*");
            }
        };
    }
}
