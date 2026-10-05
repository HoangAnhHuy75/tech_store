package com.techstore.techstore_backend.modules.user.entity;

import com.techstore.techstore_backend.modules.role.entity.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true, nullable = false)
    String username;
    String password;
    String name;
    String phone;
    LocalDate dob;

    @ManyToMany
    Set<Role> roles = new HashSet<>();
}
