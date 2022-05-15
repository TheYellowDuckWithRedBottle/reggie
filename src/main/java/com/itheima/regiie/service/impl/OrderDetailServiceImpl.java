package com.itheima.regiie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.regiie.entity.OrderDetail;
import com.itheima.regiie.mapper.OrderDetailMapper;
import com.itheima.regiie.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper,OrderDetail> implements OrderDetailService {
}
