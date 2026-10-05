package com.techstore.techstore_backend.modules.role.service;

import com.techstore.techstore_backend.modules.role.dto.request.RoleRequest;
import com.techstore.techstore_backend.modules.role.dto.response.RoleResponse;
import com.techstore.techstore_backend.modules.permission.entity.Permission;
import com.techstore.techstore_backend.modules.role.entity.Role;
import com.techstore.techstore_backend.modules.permission.repository.PermissionRepository;
import com.techstore.techstore_backend.modules.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

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
