package com.itheima.regiie.controller;

import com.itheima.regiie.common.R;
import com.itheima.regiie.entity.OrderDetail;
import com.itheima.regiie.entity.Orders;
import com.itheima.regiie.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;


}
