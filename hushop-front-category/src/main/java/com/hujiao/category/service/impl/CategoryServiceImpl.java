package com.hujiao.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hujiao.category.mapper.CategoryMapper;
import com.hujiao.category.service.CategoryService;
import com.hujiao.param.ProductHotParam;
import com.hujiao.pojo.Category;
import com.hujiao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public R byName(String categoryName) {
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("category_name",categoryName);
        Category category = categoryMapper.selectOne(categoryQueryWrapper);
        if (category==null){
            return R.fail("查无此分类");
        }
        return R.ok("查询成功",category);
    }

    @Override
    public R hotsCategory(ProductHotParam param) {
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.in("category_name",param.getCategoryName());
        categoryQueryWrapper.select("category_id");
        List<Object> ids = categoryMapper.selectObjs(categoryQueryWrapper);
        return R.ok("查询成功",ids);
    }

    @Override
    public R list() {
        List<Category> categories = categoryMapper.selectList(null);
        return R.ok("全部类别查询成功",categories);
    }
}
