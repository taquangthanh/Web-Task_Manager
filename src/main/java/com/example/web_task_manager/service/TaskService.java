package com.example.web_task_manager.service;

import com.example.web_task_manager.dto.request.TaskRequest;
import com.example.web_task_manager.dto.response.TaskResponse;
import com.example.web_task_manager.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {
    void addTask(TaskRequest taskRequest);
    void updateTask(Long id,TaskRequest taskRequest);
    void deleteTask(Long id);
    TaskEntity getById(Long id);
    List<TaskEntity> getTasksByTitle(String title);
    List<TaskEntity> getTasksByStatus(String status);
    List<TaskEntity> getTasksByFlag();
//    Page<TaskEntityJPA> findByKeyword(String keyword, Pageable pageable);
    List<TaskEntity> findAll();
//    Page<TaskEntityJPA> findByStatus(String status, Pageable pageable);
//    Page<TaskEntityJPA> findAll(Pageable pageable);
    Page<TaskEntity> findTasks(Optional<Integer> page, Optional<String> title, Optional<String> status);
}
