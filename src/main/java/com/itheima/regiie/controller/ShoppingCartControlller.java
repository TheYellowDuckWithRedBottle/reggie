package com.itheima.regiie.controller;

import com.itheima.regiie.common.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName
 * @Description TODO
 * @Author LeviFan
 * @Date 2022/5/14 22:17
 * @Version 1.0
 **/
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartControlller {

    @GetMapping("/list")
    public R<String> list(){
        return null;
    }
}
