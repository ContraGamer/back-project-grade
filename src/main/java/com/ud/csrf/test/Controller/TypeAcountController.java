package com.ud.csrf.test.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.csrf.test.Repository.TypeAcountRepository;
import com.ud.csrf.test.Model.TypeAcount;

@RestController
@RequestMapping("/type-acount")
public class TypeAcountController {
    
    @Autowired
    private TypeAcountRepository typeAcountRepository;

    @GetMapping("/getAll")
    public List<TypeAcount> getAll() {
        return typeAcountRepository.findAll();
    }
    
}
