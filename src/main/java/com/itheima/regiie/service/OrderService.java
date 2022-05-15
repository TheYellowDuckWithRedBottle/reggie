package com.itheima.regiie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.regiie.entity.Orders;
import org.springframework.core.annotation.Order;

public interface OrderService extends IService<Orders> {
    public void submit(Orders orders);
}
