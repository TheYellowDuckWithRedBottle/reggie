package com.itheima.regiie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.regiie.common.BaseContext;
import com.itheima.regiie.common.CustomException;
import com.itheima.regiie.entity.*;
import com.itheima.regiie.mapper.OrdersMapper;
import com.itheima.regiie.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrderService {

    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    UserService userService;
    @Autowired
    AddressBookService addressBookService;
    @Autowired
    OrderDetailService orderDetailService;
    @Override
    @Transactional
    public void submit(Orders order) {
        //
        Long userId = BaseContext.getCurrentId();
        //查询用户的购物车数据

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCart = shoppingCartService.list(queryWrapper);
        if(shoppingCart.size()==0){
            throw new CustomException("购物车为空");
        }

        User user = userService.getById(userId);
        AddressBook adderssBook = addressBookService.getById(order.getAddressBookId());
        if(adderssBook==null){
            throw new CustomException("用户地址信息有误，不能下单");
        }
        Long id = IdWorker.getId();
        AtomicInteger amount = new AtomicInteger(0);
        List<OrderDetail> list = shoppingCart.stream().map(item->{
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getId());
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

        order.setId(id);
        order.setCheckoutTime(LocalDateTime.now());
        order.setStatus(2);
        order.setAmount(new BigDecimal(amount.get()));//总金额
        order.setUserId(user.getId());
        order.setUserName(user.getName());
        order.setConsignee(adderssBook.getConsignee());
        order.setPhone(user.getPhone());
        order.setAddress(adderssBook.getProvinceName()+adderssBook.getCityName()+adderssBook.getDistrictName());
        order.setNumber(String.valueOf(id));

        this.save(order);
        orderDetailService.saveBatch(list);
        shoppingCartService.remove(queryWrapper);
    }
}
