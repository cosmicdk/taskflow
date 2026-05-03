package com.taskflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taskflow.dto.CategoryDTO;
import com.taskflow.entity.Category;
import java.util.List;

public interface CategoryService extends IService<Category> {
    List<Category> listByUserId(Long userId);
    Category create(Long userId, CategoryDTO dto);
    Category updateById(Long id, Long userId, CategoryDTO dto);
    void deleteById(Long id, Long userId);
}