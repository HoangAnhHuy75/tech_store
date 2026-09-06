package com.techstore.techstore_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(mappedBy = "specifications")
    Set<Category> categories = new HashSet<>();
}
