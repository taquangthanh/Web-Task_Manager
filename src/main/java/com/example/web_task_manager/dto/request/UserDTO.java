package com.example.web_task_manager.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    @NotEmpty( message = "Không được để trống")
    private String name;
    @NotEmpty( message = "Không được để trống")
    private String username;
    @NotEmpty( message = "Không được để trống")
    private String password;
    @NotEmpty( message = "Không được để trống")
    private String repeatPassword;
}
