package com.itheima.regiie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.regiie.dto.DishDto;
import com.itheima.regiie.entity.Dish;
import com.itheima.regiie.entity.Employee;
import org.springframework.stereotype.Service;


public interface DishService extends IService<Dish> {
    //新增菜品，插入口味，两张表

    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public  void updateWithFlavor(DishDto dishDto);
}
