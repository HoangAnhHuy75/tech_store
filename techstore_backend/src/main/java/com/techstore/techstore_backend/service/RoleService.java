package com.techstore.techstore_backend.service;

import com.techstore.techstore_backend.dto.request.RoleRequest;
import com.techstore.techstore_backend.dto.response.PermissionResponse;
import com.techstore.techstore_backend.dto.response.RoleResponse;
import com.techstore.techstore_backend.entity.Permission;
import com.techstore.techstore_backend.entity.Role;
import com.techstore.techstore_backend.repository.PermissionRepository;
import com.techstore.techstore_backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;
    public RoleResponse createRole(RoleRequest roleRequest){
        Role role = Role.builder()
                .name(roleRequest.getName())
                .description(roleRequest.getDescription())
                .build();
        if(roleRequest.getPermissionNames() != null){
            List<Permission> permissions = permissionRepository.findAllById(roleRequest.getPermissionNames());
            role.setPermissions(new HashSet<>(permissions));
        }
        return mapToRoleResponse(roleRepository.save(role));
    }

    public List<RoleResponse> findAll(){
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(this::mapToRoleResponse)
                .toList();
    }

    public RoleResponse mapToRoleResponse(Role role){
        return RoleResponse.builder()
                .name(role.getName())
                .description(role.getDescription())
                .permissions(role.getPermissions())
                .build();
    }
}
