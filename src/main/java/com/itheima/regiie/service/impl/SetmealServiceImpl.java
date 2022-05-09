package com.itheima.regiie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.regiie.common.CustomException;
import com.itheima.regiie.dto.SetmealDto;
import com.itheima.regiie.entity.Employee;
import com.itheima.regiie.entity.Setmeal;
import com.itheima.regiie.entity.SetmealDish;
import com.itheima.regiie.mapper.EmployeeMapper;
import com.itheima.regiie.mapper.SetmealMapper;
import com.itheima.regiie.service.EmployeeService;
import com.itheima.regiie.service.SetmealDishService;
import com.itheima.regiie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName
 * @Description TODO
 * @Author LeviFan
 * @Date 2022/5/5 13:19
 * @Version 1.0
 **/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

   @Autowired
   private SetmealDishService setmealDishService;
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //1.保存套餐基本信息(setmeal)
        this.save(setmealDto);

        //2.保存套餐与菜品的关系(setmeal_dish)
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes= setmealDishes.stream().map(item->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //1.查询套餐状态是否可以删除
        // 不能删除，抛出异常
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Setmeal::getId,ids);
        wrapper.eq(Setmeal::getStatus,1);
        int count =  this.count(wrapper);
        if(count()>0){
            throw new CustomException("套餐正在售卖无法删除");
        }

        //1.删除套餐
        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(wrapper1);
        //2.删除套餐与菜品的关系(setmeal_dish)
    }

}
