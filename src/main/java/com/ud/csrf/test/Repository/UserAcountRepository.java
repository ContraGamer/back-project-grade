package com.ud.csrf.test.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Model.UserAcount;

@Repository
public interface UserAcountRepository extends JpaRepository<UserAcount, Long>{

    Optional<List<UserAcount>> findByFinalUser(@Param("finalUser") FinalUser userAcount);

}
