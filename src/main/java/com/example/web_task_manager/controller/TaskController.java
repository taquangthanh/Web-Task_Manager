package com.example.web_task_manager.controller;

import com.example.web_task_manager.dto.request.TaskRequest;
import com.example.web_task_manager.dto.response.TaskResponse;
import com.example.web_task_manager.entity.TaskEntity;
import com.example.web_task_manager.service.TaskService;
import com.sun.net.httpserver.HttpsServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/tasklist")
public class TaskController extends BaseController {
    @Autowired
    private TaskService taskService;

    @GetMapping("")
    private String getAllByFlag(Model model , @RequestParam(name = "page" , defaultValue = "0")Integer page,
                                @RequestParam(name = "title",required = false)String title,
                                @RequestParam(name = "status",required = false)String status){
        model.addAttribute("taskRequest", new TaskRequest());
        Page<TaskEntity> taskEntities = null;
        if (StringUtils.hasText(title)){
            System.out.println(title);
            taskEntities = taskService.getTasksByTitle(title,page);
        } else if (StringUtils.hasText(status)){
            if (status == "null"){
                taskEntities = taskService.getTasksByFlag(page);
            } else {
                System.out.println(status);
                taskEntities = taskService.getTasksByStatus(status,page);
            }
        }
        else {
            taskEntities = taskService.getTasksByFlag(page);
        }
        System.out.println(taskEntities);
        model.addAttribute("listtask", taskEntities.getContent());
        model.addAttribute("currentPage", taskEntities.getNumber());
        model.addAttribute("maxPage", taskEntities.getTotalPages());
        return "listTask";
    }
    @GetMapping("/delete")
    private String deleteTask( @RequestParam(name = "id", required = false) Long id){
        taskService.deleteTask(id);
        System.out.println(id);
        System.out.println("Xóa thành công");
        return "redirect:/tasklist";
    }
    @GetMapping("/edit")
    private String editTask(Model model, @RequestParam(name = "id", required = false) Long id){
        TaskEntity task = taskService.getById(id);
        model.addAttribute("taskRequest", new TaskRequest());
        model.addAttribute("task",task);
        System.out.println(id);
        return "editTask";
    }
    @PostMapping("/update")
    private String updateTask(Model model, @ModelAttribute(name = "taskDTO") TaskRequest taskRequest,
                              @RequestParam(name = "id", required = false) Long id, HttpServletRequest request){
        String status = request.getParameter("status");
        System.out.println(status);
        taskRequest.setStatus(status);
        taskService.updateTask(id,taskRequest);
        System.out.println(taskRequest);
        System.out.println(id);
        return "redirect:/tasklist";
    }
    @GetMapping("/create-task")
    private String createTask(@ModelAttribute(name = "taskRequest") TaskRequest taskRequest, BindingResult result,
                              Model model, HttpServletRequest request){
        if (result.hasErrors()){
            model.addAttribute("taskDTO",taskRequest);
            result.toString();
            return "redirect:/tasklist";
        }
        String status = request.getParameter("status");
        System.out.println(status);
        taskRequest.setStatus(status);
        taskService.addTask(taskRequest);
        model.addAttribute("taskDTO",taskRequest);
        System.out.println(taskRequest);
        System.out.println("Lưu thành công");
        return "redirect:/tasklist";
    }
}
