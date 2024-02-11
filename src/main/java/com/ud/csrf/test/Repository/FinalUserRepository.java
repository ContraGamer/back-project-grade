package com.ud.csrf.test.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Model.Role;

@Repository
public interface FinalUserRepository extends JpaRepository<FinalUser, Long>{

    long countByIdTypeAndIdentification(@Param("idType") String IdType, @Param("identification") String identification);

    Optional<FinalUser> findByIdTypeAndIdentification(@Param("idType") String IdType, @Param("identification") String identification);

    long countByName(@Param("name") String name);

    Optional<FinalUser> findByIdTypeAndIdentificationAndPassword(@Param("idType") String IdType, @Param("identification") String identification, @Param("password") String password);

    List<FinalUser> findByRoleGreaterThan(@Param("role") Role role);
    
}
