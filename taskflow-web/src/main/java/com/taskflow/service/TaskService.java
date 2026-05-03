package com.taskflow.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taskflow.dto.TaskDTO;
import com.taskflow.dto.TaskQueryDTO;
import com.taskflow.entity.Task;

public interface TaskService extends IService<Task> {
    Page<Task> pageQuery(Long userId, TaskQueryDTO query);
    Task create(Long userId, TaskDTO dto);
    Task updateById(Long id, Long userId, TaskDTO dto);
    void deleteById(Long id, Long userId);
    Task updateStatus(Long id, Long userId, String status);
    Task getById(Long id, Long userId);
}