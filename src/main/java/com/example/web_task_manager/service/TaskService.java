package com.example.web_task_manager.service;

import com.example.web_task_manager.dto.request.TaskRequest;
import com.example.web_task_manager.dto.response.TaskResponse;
import com.example.web_task_manager.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
    TaskEntity addTask(TaskRequest taskRequest);
    TaskEntity updateTask(Long id,TaskRequest taskRequest);
    TaskEntity deleteTask(Long id);
    TaskEntity getById(Long id);
    Page<TaskEntity> getTasksByTitle(String title, Integer page);
    Page<TaskEntity> getTasksByStatus(String status, Integer page);
    Page<TaskEntity> getTasksByFlag(Integer page);
}
