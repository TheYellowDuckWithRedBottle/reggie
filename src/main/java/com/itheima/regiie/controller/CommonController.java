package com.itheima.regiie.controller;

import com.itheima.regiie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName
 * @Description TODO
 * @Author LeviFan
 * @Date 2022/5/6 11:09
 * @Version 1.0
 **/
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        String originName = file.getOriginalFilename();// 获取原始的文件名
        String suffix = originName.substring(originName.lastIndexOf("."));
        //使用UUID生成不会重复的文件名
        String name = UUID.randomUUID().toString()+suffix;

        // 判断文件是否存在
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdir();
        }

        try {
            file.transferTo(new File(basePath+name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(name);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        // 输入流
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("img/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];

            while ((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            outputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //输出流，将文件写回浏览器
    }
}