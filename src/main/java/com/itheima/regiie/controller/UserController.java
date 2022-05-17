package com.itheima.regiie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.regiie.common.R;
import com.itheima.regiie.entity.User;
import com.itheima.regiie.service.UserService;
import com.itheima.regiie.utils.SMSUtils;
import com.itheima.regiie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<Object,Object> redistemplate;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody Map user, HttpSession sesion){
        // 获取手机号
        String phone = user.get("phone").toString();

        if(StringUtils.isNotEmpty(phone)){
            // 生成随机数
            String code = ValidateCodeUtils.generateValidateCode4String(4).toString();
            //调用阿里云
            //SMSUtils.sendMessage("瑞吉外卖","",phone,code);
            System.out.println(code);
            sesion.setAttribute("code",code);

            redistemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.success("手机验证码发送成功");
        }

        //生成验证码保存到session
        return R.error("手机验证码发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map user, HttpSession session){
        String phone = user.get("phone").toString();
        String code = user.get("code").toString();

        String redisCode = redistemplate.opsForValue().get(phone).toString();

        if(redisCode.equals(code)){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            // 判断手机号对应得用户是否为新用户,新用户自动注册
            User finduser = userService.getOne(queryWrapper);
            if(finduser==null){
                finduser = new User();
                finduser.setPhone(phone);
                finduser.setStatus(1);
                userService.save(finduser);
            }
            session.setAttribute("user",finduser.getId());
            redistemplate.delete(phone);
            return R.success(finduser);
        }


        return R.error("登录失败");
    }
}
