package com.itheima.regiie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.regiie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
