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
import java.util.Optional;

@Service
public class TaskImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskEntity addTask(TaskRequest taskRequest) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(taskRequest.getTitle());
        taskEntity.setContent(taskRequest.getContent());
        taskEntity.setStatus(taskRequest.getStatus());
        taskEntity.setCreateBy("Admin");
        taskEntity.setCreateDate(new Timestamp(System.currentTimeMillis()));
        taskEntity.setModifierBy("Admin");
        taskEntity.setDeleteFlag(false);
        taskEntity.setModifierDate(new Timestamp(System.currentTimeMillis()));
        TaskResponse taskResponse =new TaskResponse();
        return taskRepository.save(taskEntity);
    }

    @Override
    public TaskEntity updateTask(Long id,TaskRequest taskRequest) {
        Optional<TaskEntity> task = taskRepository.findById(id);
        if(task.isPresent()){
            TaskEntity taskEntity = task.get();
            taskEntity.setTitle(taskRequest.getTitle() == null?taskEntity.getTitle() : taskRequest.getTitle());
            taskEntity.setContent(taskRequest.getContent() == null?taskEntity.getContent() : taskRequest.getContent());
            taskEntity.setStatus(taskRequest.getStatus() == null?taskEntity.getStatus() : taskRequest.getStatus());
            taskEntity.setCreateBy("Admin");
            taskEntity.setCreateDate(taskEntity.getCreateDate());
            taskEntity.setModifierBy("Admin");
            taskEntity.setDeleteFlag(false);
            taskEntity.setModifierDate(new Timestamp(System.currentTimeMillis()));
            return taskRepository.save(taskEntity);
        }
        return null;
    }

    @Override
    public TaskEntity deleteTask(Long id) {
        Optional<TaskEntity> task = taskRepository.findById(id);
        if(task.isPresent()){
            TaskEntity taskEntity = task.get();
            taskEntity.setTitle(taskEntity.getTitle());
            taskEntity.setContent(taskEntity.getContent());
            taskEntity.setStatus(taskEntity.getStatus());
            taskEntity.setCreateBy("Admin");
            taskEntity.setCreateDate(taskEntity.getCreateDate());
            taskEntity.setModifierBy("Admin");
            taskEntity.setDeleteFlag(true);
            taskEntity.setModifierDate(new Timestamp(System.currentTimeMillis()));
            return taskRepository.save(taskEntity);
        }
        return null;
    }

    @Override
    public TaskEntity getById(Long id) {
        Optional<TaskEntity> task = taskRepository.findById(id);
        if(task.isPresent()){
            return task.get();
        }
        return null;
    }

    @Override
    public Page<TaskEntity> getTasksByTitle(String title, Integer page) {
        return taskRepository.findByTitle("%" + title + "%", PageRequest.of(page, 5));
    }

    @Override
    public Page<TaskEntity> getTasksByStatus(String status, Integer page) {
        return taskRepository.findByStatus( "%" + status + "%", PageRequest.of(page, 5));
    }

    @Override
    public Page<TaskEntity> getTasksByFlag(Integer page) {
        return taskRepository.findByDeleteFlag(PageRequest.of(page, 5));
    }
}
