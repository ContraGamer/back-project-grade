package com.ud.csrf.test.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.csrf.test.Model.PermitRole;

@Repository
public interface PermitRoleRepository extends JpaRepository<PermitRole, Long>{
    
}
