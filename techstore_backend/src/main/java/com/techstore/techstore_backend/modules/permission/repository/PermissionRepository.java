package com.techstore.techstore_backend.modules.permission.repository;

import com.techstore.techstore_backend.modules.permission.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
