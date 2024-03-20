package com.ud.csrf.test.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ud.csrf.test.Model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{
    
    List<Request> findByOrderByDateTimeStartAsc();

    List<Request> findByOrderByDateTimeStartDesc();

    Long countByStatusHttp(@Param("statusHttp") String statusHttp);

    Long countByStatusHttpAndSecurityLevel (@Param("statusHttp") String statusHttp, @Param("securityLevel") String securityLevel);

}
