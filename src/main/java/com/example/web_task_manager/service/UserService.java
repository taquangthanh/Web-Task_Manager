package com.example.web_task_manager.service;

import com.example.web_task_manager.dto.request.UserDTO;
import com.example.web_task_manager.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findByMail(String username);
    void save(UserDTO userDTO);
}
