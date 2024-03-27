package com.hujiao.clients;

import com.hujiao.param.ProductHotParam;
import com.hujiao.pojo.Category;
import com.hujiao.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@FeignClient("category-service")
@RequestMapping("category")
public interface CategoryClient {
    @GetMapping("promo/{categoryName}")
    R byName(@PathVariable String categoryName);

    @PostMapping("hots")
    R hotsCategory(@RequestBody @Validated ProductHotParam param);

    @GetMapping("list")
    R list();
}
