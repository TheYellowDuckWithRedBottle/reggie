package com.itheima.regiie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.regiie.entity.ShoppingCart;
import com.itheima.regiie.mapper.ShoppingCartMapper;
import com.itheima.regiie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
