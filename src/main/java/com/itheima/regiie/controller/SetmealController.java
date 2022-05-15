package com.itheima.regiie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.regiie.common.R;
import com.itheima.regiie.dto.DishDto;
import com.itheima.regiie.dto.SetmealDto;
import com.itheima.regiie.entity.Category;
import com.itheima.regiie.entity.Dish;
import com.itheima.regiie.entity.Setmeal;
import com.itheima.regiie.service.CategoryService;
import com.itheima.regiie.service.SetmealDishService;
import com.itheima.regiie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName
 * @Description TODO
 * @Author LeviFan
 * @Date 2022/5/9 18:51
 * @Version 1.0
 **/
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;
    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    @Transactional
    public R<String> save(@RequestBody SetmealDto setmealDto) {
         setmealService.saveWithDish(setmealDto);
         return R.success("新转增套餐成功");
    }

    /**
     * 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper();
        wrapper.like(name!=null, Setmeal::getName, name);
        wrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, wrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dtoPage,"records");
        List<Setmeal> list = pageInfo.getRecords();
       List<SetmealDto> setMealList = list.stream().map(item->{
            SetmealDto dto = new SetmealDto();
            BeanUtils.copyProperties(item, dto);

            Long categoryId = item.getCategoryId();
            //根据分类id查询分类名称
            Category category = categoryService.getById(categoryId);
            dto.setCategoryName(category.getName());
           return dto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(setMealList);
        return R.success(dtoPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        setmealService.removeWithDish(ids);
        return R.success("删除套餐成功");
    }
    @GetMapping("/list")
    public R<List<Setmeal>> getSetmeal(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(setmeal.getCategoryId()!=null, Setmeal::getCategoryId,setmeal.getCategoryId());
        wrapper.eq(Setmeal::getStatus,setmeal.getStatus());
        wrapper.orderByDesc(Setmeal::getUpdateTime);
       List<Setmeal> listsetMeal =  setmealService.list(wrapper);
        return R.success(listsetMeal);
    }
}
