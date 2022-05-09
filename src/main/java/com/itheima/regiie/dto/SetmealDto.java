package com.itheima.regiie.dto;


import com.itheima.regiie.entity.Setmeal;
import com.itheima.regiie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
