package com.test.COCONSULT.ServiceIMP;

import com.test.COCONSULT.DTO.RoleName;
import com.test.COCONSULT.Entity.Role;
import com.test.COCONSULT.Interfaces.RoleServiceInterface;
import com.test.COCONSULT.Reposotories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class RoleServiceIMP implements RoleServiceInterface {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public void addRole(RoleName roleName) {
        Role role=new Role();
        role.setName(roleName);
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(RoleName roleName) {
        Role roleToDelete = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        roleRepository.delete(roleToDelete);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    @Override
    public void AddALLRoles(){
        for (RoleName roleName:RoleName.values()){
            addRole(roleName);
        }
    }
}
