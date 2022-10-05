package com.ud.csrf.test.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.csrf.test.DTO.CreateAcountRequestDTO;
import com.ud.csrf.test.DTO.CreateAcountResponseDTO;
import com.ud.csrf.test.Model.Acount;
import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Model.UserAcount;
import com.ud.csrf.test.Repository.UserAcountRepository;

@Service
public class UserAcountService {

    @Autowired
    UserAcountRepository userAcountRepository;

    /**
     * Asigna la cuenta creada a un usuario.
     * @param user
     * @param acount
     * @return
     */
    public boolean createAcountToUser(FinalUser user, Acount acount) {
        try {
            UserAcount userAcount = new UserAcount();
            userAcount.setFinalUser(user);
            userAcount.setAcount(acount);
            userAcount.setState("A");
            userAcountRepository.save(userAcount);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
