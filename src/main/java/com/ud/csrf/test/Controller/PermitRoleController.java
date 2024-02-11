package com.ud.csrf.test.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.csrf.test.Services.PermitRoleService;
import com.ud.csrf.test.DTO.RolListDTO;
import com.ud.csrf.test.Model.Permit;
import com.ud.csrf.test.Model.PermitRole;
import com.ud.csrf.test.Model.Role;

@RestController
@RequestMapping("/permit-role")
public class PermitRoleController {

    @Autowired
    private PermitRoleService permitRoleService;

    @GetMapping("/get-permit")
    private List<Permit> getPermitByRol(final HttpServletRequest httpServletRequest) {
        return permitRoleService.getPermitByToken(httpServletRequest);
    }

    @PostMapping("/get-permit-by-role")
    private List<Permit> getPermitByRol(final HttpServletRequest httpServletRequest, @RequestBody Role role) {
        return permitRoleService.getPermitByRole(role);
    }

    @PostMapping("/get-permit-by-role-admin")
    private List<RolListDTO> getPermitByRolAdmin(final HttpServletRequest httpServletRequest) {
        return permitRoleService.getPermitByTokenAdmin(httpServletRequest);
    }

    @GetMapping("/get-goles-and-permits-by-user")
    private List<PermitRole> getRolesAndPermitsByUser(final HttpServletRequest httpServletRequest){
        return permitRoleService.getRolesAndPermitsByUser(httpServletRequest);
    }

}
