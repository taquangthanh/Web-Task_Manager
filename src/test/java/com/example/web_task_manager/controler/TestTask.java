package com.example.web_task_manager.controler;

import com.example.web_task_manager.entity.TaskEntity;
import com.example.web_task_manager.repository.TaskRepository;
import com.example.web_task_manager.service.TaskService;
import com.example.web_task_manager.service.impl.TaskImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestTask {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService = new TaskImpl();

    private final List<TaskEntity> tasks = new ArrayList<>();

    @BeforeEach
    void setUp() {
        TaskEntity task = TaskEntity.builder()
                .id(1L)
                .title("Task1")
                .content("Hello Xin chào")
                .status("Open")
                .build();
        tasks.add(task);
    }



    @Test
    void index() {
        int currentPage = 0;
        int pageSize = 5;
        int startItem = 0;
        List<TaskEntity> taskEntities = new ArrayList<>();
        taskEntities.add(TaskEntity.builder()
                .title("Task1")
                .content("Hello Xin chào")
                .status("Open")
                .build());
        List<TaskEntity> list;
        int toIndex = Math.min(startItem + pageSize, taskEntities.size());
        list = taskEntities.subList(startItem, toIndex);
        Page<TaskEntity> taskPage
                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), tasks.size());
        assertEquals(taskPage, taskService.getByFlag(pageSize));
    }
}
