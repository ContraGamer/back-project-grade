package com.ud.csrf.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer.JwtConfigurer;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import com.ud.csrf.test.Model.Parameter;
import com.ud.csrf.test.Repository.ParameterRepository;
import com.ud.csrf.test.Services.AdditionalsService;

@Configuration
@EnableWebSecurity
public class Configurations extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{

    @Autowired
    ParameterRepository parameterRepository;

    @Autowired
    AdditionalsService additionalsService;

    @Autowired
    HandleInterceptor handleInterceptor;

    String CEROSEGURITY = "level-attack-without-security";
    String MEDIUMSECURITY = "level-attack-medium-security";
    String HIGHSECURITY = "level-attack-high-security";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //deshabilitar CSRF
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Registra el interceptor
        registry.addInterceptor(handleInterceptor);
    }
    
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
