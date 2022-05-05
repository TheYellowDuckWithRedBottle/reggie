package com.itheima.regiie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.regiie.entity.Employee;
import com.itheima.regiie.mapper.EmployeeMapper;
import com.itheima.regiie.service.EmployeeService;
import org.springframework.stereotype.Service;
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {
}
