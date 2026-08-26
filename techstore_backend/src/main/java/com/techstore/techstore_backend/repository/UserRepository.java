package com.techstore.techstore_backend.repository;

import com.techstore.techstore_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    public boolean existsByUsername(String username);
    public Optional<User> findByUsername(String username);
}
