package com.nagiel.tpspring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagiel.tpspring.model.ERole;
import com.nagiel.tpspring.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}