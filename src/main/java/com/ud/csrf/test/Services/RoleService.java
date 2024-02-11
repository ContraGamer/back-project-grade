package com.ud.csrf.test.Services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Model.Role;
import com.ud.csrf.test.Repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    FinalUserService finalUserService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermitRoleService permitRoleService;

    public List<Role> getRolesWithToken(final HttpServletRequest httpServletRequest) {
        FinalUser finalUser = finalUserService.getUserIntoSession(httpServletRequest);
        List<Role> listData = new ArrayList<Role>();
        if(finalUser.getName()!= null){
            if(finalUser.getRole().getState() == "A" && permitRoleService.isRolePermit(finalUser.getRole(), "Roles")){
                listData = roleRepository.findAll();
            }
        }
        return listData;
    }

    public List<Role> getListRoleGreaterThan(long id) {
        return roleRepository.findByIdGreaterThanEqual(id).get();
    }

}
