package com.itheima.regiie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.regiie.dto.DishDto;
import com.itheima.regiie.entity.Dish;
import com.itheima.regiie.entity.DishFlavor;
import com.itheima.regiie.mapper.DishMapper;
import com.itheima.regiie.service.DishFlavorService;
import com.itheima.regiie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName
 * @Description TODO
 * @Author LeviFan
 * @Date 2022/5/5 13:18
 * @Version 1.0
 **/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService   dishFlavorService;
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        // 保存菜品的基本信息
        this.save(dishDto);
        Long dishId =  dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        // 保存菜品的口味
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        // 查询菜品基本信息,dish
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        // 查询菜品口味
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(wrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        // 更新菜品基本信息
        this.updateById(dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        List<DishFlavor> list = dishDto.getFlavors();
        list = list.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(list);
        //  更新口味表(清理口味数据，再添加口味数据)
    }
}
