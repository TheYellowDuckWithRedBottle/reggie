package com.itheima.regiie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.regiie.common.CustomException;
import com.itheima.regiie.entity.Category;
import com.itheima.regiie.entity.Dish;
import com.itheima.regiie.entity.Setmeal;
import com.itheima.regiie.mapper.CategoryMapper;
import com.itheima.regiie.service.CategoryService;
import com.itheima.regiie.service.DishService;
import com.itheima.regiie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id){

        //当前分类是否关联了菜品，如果关联了，抛出异常
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(wrapper);

        if(count> 0){
            throw new CustomException("当前分类已经关联了菜品，不能删除");
        }

        //当前分类关联了套餐，如果关联了，抛出异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if(count2>0){
            throw new CustomException("当前分类已经关联了套餐，不能删除");
        }

        super.removeById(id);
    }

}
