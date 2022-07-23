package com.example.web_task_manager.service.impl;

import com.example.web_task_manager.dto.request.TaskRequest;
import com.example.web_task_manager.dto.response.TaskResponse;
import com.example.web_task_manager.entity.TaskEntity;
import com.example.web_task_manager.repository.TaskRepository;
import com.example.web_task_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class TaskImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskEntity addTask(TaskRequest taskRequest) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(taskRequest.getTitle());
        taskEntity.setContent(taskEntity.getContent());
        taskEntity.setStatus(taskEntity.getStatus());
        taskEntity.setCreateBy("Admin");
        taskEntity.setCreateDate(new Timestamp(System.currentTimeMillis()));
        taskEntity.setModifierBy("Admin");
        taskEntity.setDeleteFlag(true);
        taskEntity.setModifierDate(new Timestamp(System.currentTimeMillis()));
        TaskResponse taskResponse =new TaskResponse();
        return taskRepository.save(taskEntity);
    }

    @Override
    public TaskEntity updateTask(TaskRequest taskRequest) {
        return null;
    }

    @Override
    public TaskEntity deleteTask(Long id) {
        return null;
    }

    @Override
    public Page<TaskEntity> getByTitle(String title, Integer page) {
        return taskRepository.findByTitle(title, PageRequest.of(page, 5));
    }

    @Override
    public Page<TaskEntity> getByFlag(Integer page) {
        return taskRepository.findByDeleteFlag(false, PageRequest.of(page, 5));
    }
}
