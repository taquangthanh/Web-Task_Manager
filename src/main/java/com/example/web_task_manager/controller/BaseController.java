package com.example.web_task_manager.controller;

import com.example.web_task_manager.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class BaseController {
    // Trả về thông tin của đối tượng User
    @ModelAttribute("userLogined")
    public User getUserLogined() {
        Object userLogined = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userLogined != null && userLogined instanceof UserDetails)
            return (User) userLogined;
        return null;
    }
}
