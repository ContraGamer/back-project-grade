package com.ud.csrf.test.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ud.csrf.test.Model.TypeAcount;

@Repository
public interface TypeAcountRepository extends JpaRepository<TypeAcount, Long>{

    Optional<TypeAcount> findByName(@Param("name") String name);


}
