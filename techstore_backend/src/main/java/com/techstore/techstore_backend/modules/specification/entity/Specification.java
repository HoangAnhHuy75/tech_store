package com.techstore.techstore_backend.modules.specification.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "specifications")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    String unit;

}
