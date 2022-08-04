package com.example.web_task_manager.service.impl;

import com.example.web_task_manager.dto.request.TaskRequest;
import com.example.web_task_manager.entity.TaskEntity;
import com.example.web_task_manager.entity.TaskStatus;
import com.example.web_task_manager.mapper.task.TaskMapper;
import com.example.web_task_manager.service.TaskService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TaskImpl implements TaskService {


    private final TaskMapper taskMapper;

    public TaskImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public void addTask(TaskRequest taskRequest) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(taskRequest.getTitle());
        taskEntity.setContent(taskRequest.getContent());
        taskEntity.setStatus(taskRequest.getStatus());
        taskEntity.setCreateBy("Admin");
        taskEntity.setCreateDate(new Timestamp(System.currentTimeMillis()));
        taskEntity.setModifierBy("Admin");
        taskEntity.setDeleteFlag(false);
        taskEntity.setModifierDate(new Timestamp(System.currentTimeMillis()));
        taskMapper.insertAll(taskEntity);
    }

    @Override
    public void updateTask(Long id,TaskRequest taskRequest) {
        TaskEntity taskEntity = taskMapper.findById(id);
            taskEntity.setTitle(taskRequest.getTitle() == null?taskEntity.getTitle() : taskRequest.getTitle());
            taskEntity.setContent(taskRequest.getContent() == null?taskEntity.getContent() : taskRequest.getContent());
            taskEntity.setStatus(taskRequest.getStatus() == null?taskEntity.getStatus() : taskRequest.getStatus());
            taskEntity.setCreateBy("Admin");
            taskEntity.setCreateDate(taskEntity.getCreateDate());
            taskEntity.setModifierBy("Admin");
            taskEntity.setDeleteFlag(false);
            taskEntity.setModifierDate(new Timestamp(System.currentTimeMillis()));
        taskMapper.update(taskEntity);

    }

    @Override
    public void deleteTask(Long id) {
        TaskEntity taskEntity = taskMapper.findById(id);
        taskEntity.setDeleteFlag(true);
        taskMapper.delete(taskEntity);
    }

    @Override
    public TaskEntity getById(Long id) {
        return taskMapper.findById(id);
    }

    @Override
    public List<TaskEntity> getTasksByTitle(String title) {
        return taskMapper.findByTitle("%" + title + "%");
    }

    @Override
    public List<TaskEntity> getTasksByStatus(String status) {
        return taskMapper.findByStatus( "%" + status + "%");
    }
    @Override
    public List<TaskEntity> getTasksByFlag() {
        return taskMapper.findByDeleteFlag();
    }

    @Override
    public List<TaskEntity> findAll() {
        return taskMapper.findAll();
    }

    @Override
    public Page<TaskEntity> findTasks(Optional<Integer> page, Optional<String> title, Optional<String> status) {
        int currentPage = page.orElse(1);
        currentPage = Math.max(1, currentPage);
        Pageable pageable = PageRequest.of(currentPage - 1, 5);
        if (title.isPresent() && !title.get().equals("") && (status.isEmpty() || status.get().equals(""))) {
            List<TaskEntity> tasks = taskMapper.findByTitleContaining(title.get(), pageable);
            Integer countTasks = taskMapper.countTasksFilterTitle(title.get());
            return new PageImpl<>(tasks, pageable, countTasks);
        } else if (status.isPresent() && !status.get().equals("") && (title.isEmpty() || title.get().equals(""))) {
            String parseStatus = status.get().replaceAll("-", " ");
            var optionalTaskStatus = Arrays.stream(TaskStatus.values()).filter(s -> s.getCode().equals(parseStatus)).findFirst();
            if (optionalTaskStatus.isPresent()) {
                List<TaskEntity> tasks = taskMapper.findByTaskStatus(optionalTaskStatus.get(), pageable);
                Integer countTasks = taskMapper.countTasksFilterStatus(optionalTaskStatus.get());
                return new PageImpl<>(tasks, pageable, countTasks);
            } else {
                List<TaskEntity> tasks = taskMapper.findAllPage(pageable);
                Integer countTasks = taskMapper.countAllTasks();
                return new PageImpl<>(tasks, pageable, countTasks);
            }
        } else if (status.isPresent() && !status.get().equals("") && title.isPresent() && !title.get().equals("")) {
            String parseStatus = status.get().replaceAll("-", " ");
            var optionalTaskStatus = Arrays.stream(TaskStatus.values()).filter(s -> s.getCode().equals(parseStatus)).findFirst();
            if (optionalTaskStatus.isPresent()) {
                List<TaskEntity> tasks = taskMapper.findByTitleContainingAndTaskStatus(title.get(), optionalTaskStatus.get(), pageable);
                Integer countTasks = taskMapper.countTasksFilterTitleAndStatus(title.get(), optionalTaskStatus.get());
                return new PageImpl<>(tasks, pageable, countTasks);
            } else {
                List<TaskEntity> tasks = taskMapper.findByTitleContaining(title.get(), pageable);
                Integer countTasks = taskMapper.countTasksFilterTitle(title.get());
                return new PageImpl<>(tasks, pageable, countTasks);
            }
        } else {
            List<TaskEntity> tasks = taskMapper.findAllPage(pageable);
            Integer countTasks = taskMapper.countAllTasks();
            return new PageImpl<>(tasks, pageable, countTasks);
        }
    }

}
