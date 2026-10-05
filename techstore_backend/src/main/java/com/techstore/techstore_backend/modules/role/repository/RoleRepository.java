package com.techstore.techstore_backend.modules.role.repository;

import com.techstore.techstore_backend.modules.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

}
