package com.taskflow.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taskflow.common.PageResult;
import com.taskflow.common.Result;
import com.taskflow.dto.TaskDTO;
import com.taskflow.dto.TaskQueryDTO;
import com.taskflow.dto.TaskStatusDTO;
import com.taskflow.entity.Task;
import com.taskflow.service.TaskService;
import com.taskflow.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "任务接口", description = "增删改查 & 状态变更")
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "任务列表（分页+筛选）")
    @GetMapping
    public Result<PageResult<Task>> list(TaskQueryDTO query) {
        Long uid = SecurityUtils.getCurrentUserId();
        Page<Task> page = taskService.pageQuery(uid, query);
        return Result.ok(PageResult.of(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords()));
    }

    @Operation(summary = "任务详情")
    @GetMapping("/{id}")
    public Result<Task> detail(@PathVariable Long id) {
        return Result.ok(taskService.getById(id, SecurityUtils.getCurrentUserId()));
    }

    @Operation(summary = "创建任务")
    @PostMapping
    public Result<Task> create(@Valid @RequestBody TaskDTO dto) {
        return Result.ok("创建成功", taskService.create(SecurityUtils.getCurrentUserId(), dto));
    }

    @Operation(summary = "更新任务")
    @PutMapping("/{id}")
    public Result<Task> update(@PathVariable Long id, @Valid @RequestBody TaskDTO dto) {
        return Result.ok("更新成功", taskService.updateById(id, SecurityUtils.getCurrentUserId(), dto));
    }

    @Operation(summary = "删除任务")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        taskService.deleteById(id, SecurityUtils.getCurrentUserId());
        return Result.ok("删除成功");
    }

    @Operation(summary = "修改任务状态")
    @PatchMapping("/{id}/status")
    public Result<Task> updateStatus(@PathVariable Long id, @RequestBody TaskStatusDTO dto) {
        return Result.ok(taskService.updateStatus(id, SecurityUtils.getCurrentUserId(), dto.getStatus()));
    }
}