package com.ud.csrf.test.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.csrf.test.Repository.ParameterRepository;
import com.ud.csrf.test.DTO.GetAllParamsWebRequestDTO;
import com.ud.csrf.test.Model.Parameter;

@RestController
@RequestMapping("/params")
public class ParameterController {

    @Autowired
    ParameterRepository parameterRepository;

    @PostMapping("/securityParams")
    private List<Parameter> securityParams(@RequestBody GetAllParamsWebRequestDTO getAllParamsWebRequestDTO){
        System.out.println("of: ".concat(getAllParamsWebRequestDTO.getOf()));
        return parameterRepository.findByOf(getAllParamsWebRequestDTO.getOf()).get();
    }

    @GetMapping("/getParams")
    private List<Parameter> getParams(){
        return parameterRepository.findAll();
    }
    
}