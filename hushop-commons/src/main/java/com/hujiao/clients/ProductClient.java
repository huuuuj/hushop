package com.hujiao.clients;

import com.hujiao.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("product-service")
@RequestMapping("product")
public interface ProductClient {
    @GetMapping("/list")
    List<Product> allList();
}
