package com.example.web_task_manager.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private String title;
    private String content;
    private String status;
}
