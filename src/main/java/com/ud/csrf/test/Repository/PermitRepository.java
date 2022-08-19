package com.ud.csrf.test.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.csrf.test.Model.Permit;

@Repository
public interface PermitRepository extends JpaRepository<Permit, Long>{
    
}
