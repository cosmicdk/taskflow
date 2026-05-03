package com.taskflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taskflow.common.BusinessException;
import com.taskflow.constant.TaskConstants;
import com.taskflow.dto.TaskDTO;
import com.taskflow.dto.TaskQueryDTO;
import com.taskflow.entity.Task;
import com.taskflow.mapper.TaskMapper;
import com.taskflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;

@Slf4j
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Override
    public Page<Task> pageQuery(Long userId, TaskQueryDTO query) {
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getUserId, userId);
        if (StringUtils.hasText(query.getStatus())) wrapper.eq(Task::getStatus, query.getStatus());
        if (StringUtils.hasText(query.getPriority())) wrapper.eq(Task::getPriority, query.getPriority());
        if (query.getCategoryId() != null) wrapper.eq(Task::getCategoryId, query.getCategoryId());
        if (StringUtils.hasText(query.getKeyword())) wrapper.like(Task::getTitle, query.getKeyword());
        wrapper.orderByDesc(Task::getCreateTime);
        return this.page(new Page<>(query.getPage(), query.getSize()), wrapper);
    }

    @Override @Transactional
    public Task create(Long userId, TaskDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority() != null ? dto.getPriority() : TaskConstants.PRIORITY_MEDIUM);
        task.setStatus(dto.getStatus() != null ? dto.getStatus() : TaskConstants.STATUS_TODO);
        task.setCategoryId(dto.getCategoryId());
        task.setUserId(userId);
        task.setDueDate(dto.getDueDate());
        this.save(task);
        return task;
    }

    @Override @Transactional
    public Task updateById(Long id, Long userId, TaskDTO dto) {
        Task task = this.getById(id);
        if (task == null) throw new BusinessException(404, "任务不存在");
        if (!task.getUserId().equals(userId)) throw new BusinessException(403, "无权操作该任务");
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        if (dto.getPriority() != null) task.setPriority(dto.getPriority());
        if (dto.getStatus() != null) task.setStatus(dto.getStatus());
        task.setCategoryId(dto.getCategoryId());
        task.setDueDate(dto.getDueDate());
        if (TaskConstants.STATUS_DONE.equals(task.getStatus()) && task.getCompletedAt() == null)
            task.setCompletedAt(LocalDateTime.now());
        this.updateById(task);
        return task;
    }

    @Override @Transactional
    public void deleteById(Long id, Long userId) {
        Task task = this.getById(id);
        if (task == null) throw new BusinessException(404, "任务不存在");
        if (!task.getUserId().equals(userId)) throw new BusinessException(403, "无权操作该任务");
        this.removeById(id);
    }

    @Override @Transactional
    public Task updateStatus(Long id, Long userId, String status) {
        Task task = this.getById(id);
        if (task == null) throw new BusinessException(404, "任务不存在");
        if (!task.getUserId().equals(userId)) throw new BusinessException(403, "无权操作该任务");
        task.setStatus(status);
        if (TaskConstants.STATUS_DONE.equals(status)) task.setCompletedAt(LocalDateTime.now());
        this.updateById(task);
        return task;
    }

    @Override
    public Task getById(Long id, Long userId) {
        Task task = this.getById(id);
        if (task == null) throw new BusinessException(404, "任务不存在");
        if (!task.getUserId().equals(userId)) throw new BusinessException(403, "无权操作该任务");
        return task;
    }
}