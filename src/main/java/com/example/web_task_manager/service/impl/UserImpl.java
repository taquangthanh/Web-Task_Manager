package com.example.web_task_manager.service.impl;

import com.example.web_task_manager.dto.request.UserDTO;
import com.example.web_task_manager.entity.User;
import com.example.web_task_manager.mapper.user.UserMapper;
import com.example.web_task_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {

    //    @Autowired
//    private RoleResponsitory roleResponsitory;
//    @Autowired
//    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByMail(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void save(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
//       user.setRoles(Arrays.asList(roleResponsitory.findByName("ADMIN")));
        userMapper.insertAll(user);
    }
}
