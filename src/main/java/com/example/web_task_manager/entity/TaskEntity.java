package com.example.web_task_manager.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskEntity extends Base {
    private Long id;
    private String title;
    private String content;
    private String status;
}
