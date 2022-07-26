package com.example.web_task_manager.controler;

import com.example.web_task_manager.controller.LoginController;
import com.example.web_task_manager.dto.request.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoginTest {
    @Mock
    LoginController loginController;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = userDTO.builder()
                .name("Thành đây này mọi ngườiơi")
                .password("123456")
                .email("thanhtqph17187@fpt.edu.vn")
                .build();
    }

    @Test
    void signInView() {
    }

    @Test
    void signIn() {
    }

    @Test
    void signUpView() {
    }

    @Test
    void testSignIn() {
    }
}
