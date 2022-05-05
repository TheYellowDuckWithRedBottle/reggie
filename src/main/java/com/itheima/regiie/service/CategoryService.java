package com.itheima.regiie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.regiie.entity.Category;
import org.springframework.stereotype.Service;


public interface CategoryService extends IService<Category> {

    void remove(Long id);
}
