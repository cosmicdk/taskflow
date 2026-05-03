package com.taskflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taskflow.common.BusinessException;
import com.taskflow.dto.CategoryDTO;
import com.taskflow.entity.Category;
import com.taskflow.mapper.CategoryMapper;
import com.taskflow.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public List<Category> listByUserId(Long userId) {
        return this.list(new LambdaQueryWrapper<Category>().eq(Category::getUserId, userId).orderByAsc(Category::getSortOrder));
    }

    @Override @Transactional
    public Category create(Long userId, CategoryDTO dto) {
        Category c = new Category();
        c.setName(dto.getName());
        c.setColor(dto.getColor());
        c.setUserId(userId);
        c.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0);
        this.save(c);
        return c;
    }

    @Override @Transactional
    public Category updateById(Long id, Long userId, CategoryDTO dto) {
        Category c = this.getById(id);
        if (c == null) throw new BusinessException(404, "分类不存在");
        if (!c.getUserId().equals(userId)) throw new BusinessException(403, "无权操作");
        c.setName(dto.getName());
        c.setColor(dto.getColor());
        if (dto.getSortOrder() != null) c.setSortOrder(dto.getSortOrder());
        this.updateById(c);
        return c;
    }

    @Override @Transactional
    public void deleteById(Long id, Long userId) {
        Category c = this.getById(id);
        if (c == null) throw new BusinessException(404, "分类不存在");
        if (!c.getUserId().equals(userId)) throw new BusinessException(403, "无权操作");
        this.removeById(id);
    }
}