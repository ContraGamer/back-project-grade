package com.ud.csrf.test.Services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Model.Permit;
import com.ud.csrf.test.Model.PermitRole;
import com.ud.csrf.test.Model.Role;
import com.ud.csrf.test.Repository.PermitRoleRepository;

@Service
public class PermitRoleService {

    @Autowired
    PermitRoleRepository permitRoleRepository;

    @Autowired
    AdditionalsService additionalsService;

    public List<Permit> getPermitByToken(final HttpServletRequest httpServletRequest) {
        List<Permit> listRes = new ArrayList<Permit>();
        try {
            FinalUser finalUser = additionalsService.getUserToToken("secret", additionalsService.getToken(httpServletRequest));
            for (PermitRole permitRole: permitRoleRepository.findByRole(finalUser.getRole()).get()) {
                Permit permit = new Permit();
                permit = permitRole.getPermit();
                listRes.add(permit);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRes;
    }

    public List<Permit> getPermitByRole(Role role) {
        List<Permit> listRes = new ArrayList<Permit>();
        try {
            for (PermitRole permitRole: permitRoleRepository.findByRole(role).get()) {
                Permit permit = new Permit();
                permit = permitRole.getPermit();
                listRes.add(permit);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRes;
    }

}
