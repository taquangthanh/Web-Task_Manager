package com.example.web_task_manager.service.impl;

import com.example.web_task_manager.dto.request.UserDTO;
import com.example.web_task_manager.entity.User;
import com.example.web_task_manager.repository.RoleResponsitory;
import com.example.web_task_manager.repository.UserRepository;
import com.example.web_task_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserImpl implements UserService {

    @Autowired
    private RoleResponsitory roleResponsitory;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByMail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setRoles(Arrays.asList(roleResponsitory.findByName("ADMIN")));
        return userRepository.save(user);
    }
}
