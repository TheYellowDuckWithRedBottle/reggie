package com.itheima.regiie.controller;

import com.itheima.regiie.common.R;
import com.itheima.regiie.dto.DishDto;
import com.itheima.regiie.service.CategoryService;
import com.itheima.regiie.service.DishFlavorService;
import com.itheima.regiie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){


        return null;
    }
}
