package com.vi3nty.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vi3nty.blog.entity.Article;
import com.vi3nty.blog.entity.Category;
import com.vi3nty.blog.mapper.CategoryMapper;
import com.vi3nty.blog.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : vi3nty
 * @date : 21:18 2019/9/18
 */
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> getAll() {
        return categoryMapper.selectList(null);
    }

    @Override
    public IPage<Category> getCategoriesByPage(int currentPage, int pageSize) {
        IPage<Category> page=new Page<>(currentPage,pageSize);
        return categoryMapper.selectPage(page,null);
    }
}
