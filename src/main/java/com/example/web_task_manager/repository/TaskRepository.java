package com.example.web_task_manager.repository;

import com.example.web_task_manager.dto.response.TaskResponse;
import com.example.web_task_manager.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> {

    @Query("SELECT c FROM TaskEntity c WHERE c.deleteFlag = false and c.title like %?1%")
    Page<TaskEntity> findByTitle(String title, Pageable pageable);

    @Query("SELECT c FROM TaskEntity c WHERE c.deleteFlag = false ")
    Page<TaskEntity> findByDeleteFlag( Pageable pageable);

    @Query("SELECT c FROM TaskEntity c WHERE c.deleteFlag = false and c.status like %?1%")
    Page<TaskEntity> findByStatus(String status, Pageable pageable);
}
