package com.techstore.techstore_backend.modules.role.entity;

import com.techstore.techstore_backend.modules.permission.entity.Permission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    String name;
    String description;

    @ManyToMany
    Set<Permission> permissions = new HashSet<>();
}
