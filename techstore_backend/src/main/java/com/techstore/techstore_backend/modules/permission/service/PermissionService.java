package com.techstore.techstore_backend.modules.permission.service;

import com.techstore.techstore_backend.modules.permission.dto.request.PermissionRequest;
import com.techstore.techstore_backend.modules.permission.dto.response.PermissionResponse;
import com.techstore.techstore_backend.modules.permission.entity.Permission;
import com.techstore.techstore_backend.modules.permission.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    public PermissionResponse createPermission(PermissionRequest request){

        Permission permission = Permission.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return mapToPermissionResponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> findAll(){
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(this::mapToPermissionResponse)
                .toList();
    }
    public PermissionResponse mapToPermissionResponse(Permission permission){
        return PermissionResponse.builder()
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }
}
