package com.vi3nty.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vi3nty.blog.entity.Category;

import java.util.List;

/**
 * @author : vi3nty
 * @date : 21:17 2019/9/18
 */
public interface ICategoryService {
    List<Category> getAll();
    IPage<Category> getCategoriesByPage(int currentPage, int pageSize);
}
