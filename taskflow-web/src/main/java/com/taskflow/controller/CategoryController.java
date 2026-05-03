package com.taskflow.controller;

import com.taskflow.common.Result;
import com.taskflow.dto.CategoryDTO;
import com.taskflow.entity.Category;
import com.taskflow.service.CategoryService;
import com.taskflow.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "分类接口", description = "任务分类管理")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "分类列表")
    @GetMapping
    public Result<List<Category>> list() {
        return Result.ok(categoryService.listByUserId(SecurityUtils.getCurrentUserId()));
    }

    @Operation(summary = "创建分类")
    @PostMapping
    public Result<Category> create(@Valid @RequestBody CategoryDTO dto) {
        return Result.ok("创建成功", categoryService.create(SecurityUtils.getCurrentUserId(), dto));
    }

    @Operation(summary = "更新分类")
    @PutMapping("/{id}")
    public Result<Category> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto) {
        return Result.ok("更新成功", categoryService.updateById(id, SecurityUtils.getCurrentUserId(), dto));
    }

    @Operation(summary = "删除分类")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.deleteById(id, SecurityUtils.getCurrentUserId());
        return Result.ok("删除成功");
    }
}