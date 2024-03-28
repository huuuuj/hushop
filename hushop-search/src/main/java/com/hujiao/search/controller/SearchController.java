package com.hujiao.search.controller;

import com.hujiao.param.ProductSearchParam;
import com.hujiao.search.service.SearchService;
import com.hujiao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("search")
public class SearchController {
    @Autowired
    private SearchService searchService;
    @PostMapping("product")
    public R searchProduct(@RequestBody ProductSearchParam param) {
        return searchService.search(param);
    }
}
