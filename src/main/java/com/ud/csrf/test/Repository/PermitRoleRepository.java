package com.ud.csrf.test.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ud.csrf.test.Model.PermitRole;
import com.ud.csrf.test.Model.Role;

@Repository
public interface PermitRoleRepository extends JpaRepository<PermitRole, Long>{

        Optional<List<PermitRole>> findByRole(@Param("role") Role role);
    
}
