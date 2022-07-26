package com.example.web_task_manager.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private String repeatPassword;
}
