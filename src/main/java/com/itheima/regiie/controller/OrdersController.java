package com.itheima.regiie.controller;

import com.itheima.regiie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class OrdersController {
    @Autowired
    private OrderService orderService;
}
