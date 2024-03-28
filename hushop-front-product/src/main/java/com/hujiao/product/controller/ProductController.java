package com.hujiao.product.controller;

import com.hujiao.param.*;
import com.hujiao.product.service.ProductService;
import com.hujiao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/promo")
    public R promo(@RequestBody @Validated ProductPromoParam param, BindingResult result){
        if (result.hasErrors()){
            return R.fail("查询失败");
        }
        return productService.promo(param.getCategoryName());
    }
    @PostMapping("hots")
    public R hots(@RequestBody @Validated ProductHotParam param, BindingResult result){
        if (result.hasErrors()){
            return R.fail("查询失败");
        }
        return productService.hots(param);
    }

    @PostMapping("category/list")
    public R clist(){
        return productService.clist();
    }

    @PostMapping("bycategory")
    public R byCategory(@RequestBody @Validated ProductIdsParam param,BindingResult result){
        if (result.hasErrors()){
            return R.fail("error");
        }
        return productService.byCategory(param);
    }

    @PostMapping("all")
    public R all(@RequestBody @Validated ProductIdsParam param,BindingResult result){
        if (result.hasErrors()){
            return R.fail("error");
        }
        return productService.byCategory(param);
    }

    @PostMapping("detail")
    public R detail(@RequestBody @Validated ProductIdParam param, BindingResult result){
        if (result.hasErrors()){
            return R.fail("error");
        }
        return productService.detail(param.getProductID());
    }
    @PostMapping("pictures")
    public R pictures(@RequestBody @Validated ProductIdParam param, BindingResult result){
        if (result.hasErrors()){
            return R.fail("error");
        }
        return productService.pictures(param.getProductID());
    }

    @PostMapping("search")
    public R search(@RequestBody ProductSearchParam param){
        return productService.search(param);
    }
}
