package com.ud.csrf.test.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.csrf.test.Model.Parameter;
import com.ud.csrf.test.Repository.ParameterRepository;

@Service
public class ParameterService {

    @Autowired
    ParameterRepository parameterRepository;

    public List<Parameter> getOthersParams(String of) {
        List<Parameter> otherParams = new ArrayList<>();
        for (Parameter parameter : parameterRepository.findAll()) {
            if (!parameter.getOf().endsWith(of)) {
                otherParams.add(parameter);
            }
        }
        return otherParams;
    }

}
