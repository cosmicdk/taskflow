package com.taskflow.dto;
import lombok.Data;

@Data
public class TaskQueryDTO {
    private String status;
    private String priority;
    private Long categoryId;
    private String keyword;
    private Long page = 1L;
    private Long size = 10L;
}