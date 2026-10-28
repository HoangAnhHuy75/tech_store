package com.techstore.techstore_backend.modules.user.entity;

import com.techstore.techstore_backend.modules.role.entity.Role;
import com.techstore.techstore_backend.modules.user.enums.UserType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name="username", unique = true, nullable = false)
    String username;

    @Column(name="password", nullable = false)
    String password;

    @Column(name="name")
    String name;

    @Column(name="phone")
    String phone;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    UserType userType = UserType.NORMAL;

    @ManyToMany
    Set<Role> roles = new HashSet<>();
}
