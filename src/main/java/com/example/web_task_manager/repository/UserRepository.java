package com.example.web_task_manager.repository;

import com.example.web_task_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select o from User o where o.username = ?1")
    User findByUsername(String email);
}
