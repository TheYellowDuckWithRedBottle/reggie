package com.itheima.regiie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.regiie.dto.SetmealDto;
import com.itheima.regiie.entity.Setmeal;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);
    //删除套餐同时删除菜品
    public void removeWithDish(List<Long> id);
}
