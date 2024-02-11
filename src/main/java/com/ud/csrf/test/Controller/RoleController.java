package com.ud.csrf.test.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.csrf.test.Model.FinalUser;
import com.ud.csrf.test.Model.Role;
import com.ud.csrf.test.Services.AdditionalsService;
import com.ud.csrf.test.Services.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AdditionalsService additionalsService;

    @GetMapping("/get-roles")
    private List<Role> getPermitByRol(final HttpServletRequest httpServletRequest){
        return roleService.getRolesWithToken(httpServletRequest);
    }

    @GetMapping("/get-roles-to-permit")
    private List<Role> getRolesToPermit (final HttpServletRequest httpServletRequest){
        FinalUser finalUser = additionalsService.getUserToToken("secret",
            additionalsService.getToken(httpServletRequest));
        return roleService.getListRoleGreaterThan(finalUser.getRole().getId());
    }

}
