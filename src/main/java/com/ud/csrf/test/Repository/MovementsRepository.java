package com.ud.csrf.test.Repository;

import org.springframework.stereotype.Repository;

import com.ud.csrf.test.Model.Movements;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface MovementsRepository extends JpaRepository<Movements, Long>{

    List<Movements> findByAcountFirst(@Param("acountFirst") String acountFirst);
    List<Movements> findByAcountSecond(@Param("acountSecond") String acountSecond);

    Movements findByCode(@Param("code") String code);
    
}
