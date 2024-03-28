package com.hujiao.clients;

import com.hujiao.param.ProductSearchParam;
import com.hujiao.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "search-service")
@RequestMapping("search")
public interface SearchClient {
    @PostMapping("product")
    R searchProduct(@RequestBody ProductSearchParam param);
}
