package com.spring.cinemaapp.repository;

import com.spring.cinemaapp.model.Role;
import com.spring.cinemaapp.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleType(RoleType roleType);
}
