package com.example.consumerestapijava.repository;

import com.example.consumerestapijava.models.ERole;
import com.example.consumerestapijava.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
