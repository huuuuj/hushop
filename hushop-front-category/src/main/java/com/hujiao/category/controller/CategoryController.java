package com.hujiao.category.controller;

import com.hujiao.category.service.CategoryService;
import com.hujiao.param.ProductHotParam;
import com.hujiao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/promo/{categoryName}")
    public R byName(@PathVariable String categoryName) {
        if (categoryName.isEmpty()) {
            return R.fail("类别为空，无法查询");
        }
    return categoryService.byName(categoryName);
    }

    /**
     * 热门分类id查询
     * @param param
     * @param result
     * @return id集合
     */
    @PostMapping("hots")
    public R hotsCategory(@RequestBody @Validated ProductHotParam param, BindingResult result){
        if (result.hasErrors()){
            return R.fail("查询失败");
        }
        return categoryService.hotsCategory(param);
    }

    @GetMapping("list")
    public R list(){
        return categoryService.list();
    }

}
