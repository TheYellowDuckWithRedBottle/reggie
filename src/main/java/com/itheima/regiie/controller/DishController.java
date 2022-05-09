package com.itheima.regiie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.regiie.common.R;
import com.itheima.regiie.dto.DishDto;
import com.itheima.regiie.entity.Category;
import com.itheima.regiie.entity.Dish;
import com.itheima.regiie.service.CategoryService;
import com.itheima.regiie.service.DishFlavorService;
import com.itheima.regiie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName
 * @Description TODO
 * @Author LeviFan
 * @Date 2022/5/6 18:19
 * @Version 1.0
 **/
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
   private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @GetMapping("/page")
    public R<Page> list(int page,int pageSize,String name){
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name!=null,Dish::getName,name);

        wrapper.orderByDesc(Dish::getCreateTime);
        dishService.page(pageInfo,wrapper);

        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> dishDtos = records.stream().map(item->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category!=null){
                dishDto.setCategoryName(category.getName());
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(dishDtos);
        return R.success(dishDtoPage);
    }

    /**
     * 根据id查询菜品信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dto = dishService.getByIdWithFlavor(id);
        return R.success(dto);
    }

    /**
     * 修改菜品信息
     *
     * */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){

        return R.success("修改菜品成功");
    }
}
