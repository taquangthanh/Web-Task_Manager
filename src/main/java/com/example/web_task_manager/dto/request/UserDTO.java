package com.example.web_task_manager.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private String name;
    private String username;
    private String password;
    private String repeatPassword;
}
