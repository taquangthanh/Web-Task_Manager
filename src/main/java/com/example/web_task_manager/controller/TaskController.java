package com.example.web_task_manager.controller;

import com.example.web_task_manager.dto.response.TaskResponse;
import com.example.web_task_manager.entity.TaskEntity;
import com.example.web_task_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/tasklist")
    private String getAllByFlag(Model model , @RequestParam(name = "page" , defaultValue = "0")Integer page){
        Page<TaskEntity> taskEntities = taskService.getByFlag(page);
        System.out.println(taskEntities);
        model.addAttribute("listtask", taskEntities.getContent());
        model.addAttribute("currentPage", taskEntities.getNumber());
        model.addAttribute("maxPage", taskEntities.getTotalPages());
        return "listTask";
    }

}
