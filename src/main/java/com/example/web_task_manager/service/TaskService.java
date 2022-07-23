package com.example.web_task_manager.service;

import com.example.web_task_manager.dto.request.TaskRequest;
import com.example.web_task_manager.dto.response.TaskResponse;
import com.example.web_task_manager.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
    TaskEntity addTask(TaskRequest taskRequest);
    TaskEntity updateTask(TaskRequest taskRequest);
    TaskEntity deleteTask(Long id);
    Page<TaskEntity> getByTitle(String title, Integer page);
    Page<TaskEntity> getByFlag(Integer page);
}
