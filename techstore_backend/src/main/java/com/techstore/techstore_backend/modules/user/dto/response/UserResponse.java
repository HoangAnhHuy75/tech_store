package com.techstore.techstore_backend.modules.user.dto.response;

import com.techstore.techstore_backend.modules.role.entity.Role;
import com.techstore.techstore_backend.modules.user.enums.UserType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Integer id;
    String name;
    String phone;
    String username;
    String password;
    UserType userType;
    Set<Role> roles;
}
