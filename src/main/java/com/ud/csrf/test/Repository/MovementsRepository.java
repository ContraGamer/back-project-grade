package com.ud.csrf.test.Repository;

import org.springframework.stereotype.Repository;

import com.ud.csrf.test.Model.Movements;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MovementsRepository extends JpaRepository<Movements, Long>{
    
}
