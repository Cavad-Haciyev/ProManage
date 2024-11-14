package com.cavad.promanage.service;

import com.cavad.promanage.model.Role;
import com.cavad.promanage.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Role getRoleById(Long id){
        Role byId = roleRepository.findById(id).orElseThrow();
        return byId;
    }
}
