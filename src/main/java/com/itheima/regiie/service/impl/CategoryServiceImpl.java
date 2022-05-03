package com.itheima.regiie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.regiie.entity.Category;
import com.itheima.regiie.mapper.CategoryMapper;
import com.itheima.regiie.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
