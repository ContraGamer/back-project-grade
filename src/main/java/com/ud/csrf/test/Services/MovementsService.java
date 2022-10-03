package com.ud.csrf.test.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.csrf.test.Repository.MovementsRepository;

@Service
public class MovementsService {
    
    @Autowired
    MovementsRepository movementsRepository;
    
}
