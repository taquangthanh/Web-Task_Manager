package com.example.web_task_manager.repository;

import com.example.web_task_manager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleResponsitory  extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
