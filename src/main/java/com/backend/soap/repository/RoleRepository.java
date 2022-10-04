package com.backend.soap.repository;

import com.backend.soap.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
