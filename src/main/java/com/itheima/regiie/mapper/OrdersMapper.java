package com.itheima.regiie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.regiie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.core.annotation.Order;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
