package com.ud.csrf.test.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.csrf.test.Repository.UserAcountRepository;

@Service
public class UserAcountService {

    @Autowired
    UserAcountRepository acountRepository;
    
}
