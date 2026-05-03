package com.taskflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TaskDTO {
    @NotBlank(message = "任务标题不能为空")
    private String title;
    private String description;
    private String priority;
    private String status;
    private Long categoryId;
    private LocalDate dueDate;
}