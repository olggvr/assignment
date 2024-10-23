package org.example.task.services;

import jakarta.transaction.Transactional;
import org.example.task.models.Role;
import org.example.task.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void createRole(String name){
        Role role = new Role(name);
        roleRepository.save(role);
    }

    @Transactional
    public Role findRole(String name){
        return roleRepository.findByName(name);
    }

}
