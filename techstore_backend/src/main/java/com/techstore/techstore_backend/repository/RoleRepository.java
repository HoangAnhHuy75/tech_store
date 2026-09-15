package com.techstore.techstore_backend.repository;

import com.techstore.techstore_backend.entity.Permission;
import com.techstore.techstore_backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, String> {

}
