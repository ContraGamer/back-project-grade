package com.ud.csrf.test.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.csrf.test.Model.UserAcount;
import com.ud.csrf.test.Repository.UserAcountRepository;

@RestController
@RequestMapping("/user-acount")
public class UserAcountController {

    @Autowired
    private UserAcountRepository userAcountRepository;

    @GetMapping("/getAll")
    public List<UserAcount> getAll() {
        return userAcountRepository.findAll();
    }

}
