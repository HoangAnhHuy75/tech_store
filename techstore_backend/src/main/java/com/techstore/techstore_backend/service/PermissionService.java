package com.techstore.techstore_backend.service;

import com.techstore.techstore_backend.dto.request.PermissionRequest;
import com.techstore.techstore_backend.dto.response.PermissionResponse;
import com.techstore.techstore_backend.entity.Permission;
import com.techstore.techstore_backend.repository.PermissionRepository;
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
