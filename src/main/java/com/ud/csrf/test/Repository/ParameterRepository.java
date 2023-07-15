package com.ud.csrf.test.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ud.csrf.test.Model.Parameter;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long>{

    Optional<List<Parameter>> findByOfAndState(@Param("of") String of, @Param("state") String state);
    
    Optional<List<Parameter>> findByOf(@Param("of") String of);


}
