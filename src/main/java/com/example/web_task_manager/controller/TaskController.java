package com.example.web_task_manager.controller;

import com.example.web_task_manager.dto.request.TaskRequest;
import com.example.web_task_manager.entity.TaskEntity;
import com.example.web_task_manager.mapper.task.TaskMapper;
import com.example.web_task_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasklist")
public class TaskController extends BaseController {
    @Autowired
    private TaskService taskService;
    @GetMapping("/list2")
    public String showPage2(
            Model model,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> title,
            @RequestParam Optional<String> status){
        Page<TaskEntity> tasks = taskService.findTasks(page, title, status);
        model.addAttribute("task", tasks);
        return "listTasks";
    }

    @GetMapping("")
    private String getAllByFlag(Model model,
                                @RequestParam Optional<Integer> page,
                                @RequestParam Optional<String> title,
                                @RequestParam Optional<String> status) {

        Page<TaskEntity> tasks = taskService.findTasks(page, title, status);
        model.addAttribute("task", tasks);
        model.addAttribute("taskRequest", new TaskRequest());
        return "listTask";
    }

    @GetMapping("/delete/{id}")
    private String deleteTask(@PathVariable(name = "id") Long id) {
        taskService.deleteTask(id);
        System.out.println(id);
        System.out.println("Xóa thành công");
        return "redirect:/tasklist";
    }

    @GetMapping("/edit")
    private String editTask(Model model, @RequestParam(name = "id", required = false) Long id) {
        TaskEntity task = taskService.getById(id);
        model.addAttribute("taskRequest", new TaskRequest());
        model.addAttribute("task", task);
        System.out.println(id);
        return "editTask";
    }
    @GetMapping("/detail")
    private String detailTask(Model model, @RequestParam(name = "id", required = false) Long id) {
        TaskEntity task = taskService.getById(id);
        model.addAttribute("task", task);
        System.out.println(id);
        return "detailTask";
    }

    @PostMapping("/update")
    private String updateTask(Model model, @ModelAttribute(name = "taskDTO") TaskRequest taskRequest,
                              @RequestParam(name = "id", required = false) Long id, HttpServletRequest request) {
        String status = request.getParameter("status");
        System.out.println(status);
        taskRequest.setStatus(status);
        taskService.updateTask(id, taskRequest);
        System.out.println(taskRequest);
        System.out.println(id);
        return "redirect:/tasklist";
    }

    @GetMapping("/create-task")
    private String createTask(@ModelAttribute(name = "taskRequest") TaskRequest taskRequest, BindingResult result,
                              Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("taskDTO", taskRequest);
            result.toString();
            return "redirect:/tasklist";
        }
        String status = request.getParameter("status");
        System.out.println(status);
        taskRequest.setStatus(status);
        taskService.addTask(taskRequest);
        model.addAttribute("taskDTO", taskRequest);
        System.out.println(taskRequest);
        System.out.println("Lưu thành công");
        return "redirect:/tasklist";
    }

    @GetMapping("/export")
    private void exportCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String fileName = "tasks"+currentDateTime+ ".csv";

        String headerKey =  "Content-Disposition";
        String  headerValue = "attachment; filename=" + fileName;

        response.setHeader(headerKey, headerValue);

        List<TaskEntity> taskEntities = taskService.findAll();

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] cvsHeader = {"Task ID", "Title", "Content", "Status" };

        String[] mapping = {"id", "title", "content", "status"};

        csvBeanWriter.writeHeader(cvsHeader);

        for (TaskEntity taskEntity : taskEntities){
            csvBeanWriter.write(taskEntity,mapping);
        }
        csvBeanWriter.close();
    }
}
