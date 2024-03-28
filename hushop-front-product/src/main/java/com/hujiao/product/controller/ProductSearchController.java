package com.hujiao.product.controller;

import com.hujiao.param.ProductHotParam;
import com.hujiao.param.ProductIdParam;
import com.hujiao.param.ProductIdsParam;
import com.hujiao.param.ProductPromoParam;
import com.hujiao.pojo.Product;
import com.hujiao.product.service.ProductService;
import com.hujiao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductSearchController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> allList(){
        return productService.allList();
    }


}
