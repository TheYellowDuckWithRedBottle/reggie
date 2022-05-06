package com.itheima.regiie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.regiie.common.R;
import com.itheima.regiie.entity.Category;
import com.itheima.regiie.service.CategoryService;
import com.itheima.regiie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService cateService;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category");
        cateService.save(category);
        return R.success("新增分类成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        Page<Category> pageInfo = new Page<>(page,pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);

        cateService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("删除的菜品");
        cateService.remove(ids);
        return R.success("分类信息删除成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category _category){
        log.info("修改分类");
        cateService.updateById(_category);
        return null;
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        // 条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        //添加条件
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        // 添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = cateService.list(queryWrapper);
        return R.success(list);
    }
}
