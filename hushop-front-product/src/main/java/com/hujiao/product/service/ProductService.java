package com.hujiao.product.service;

import com.hujiao.param.ProductHotParam;
import com.hujiao.param.ProductIdsParam;
import com.hujiao.utils.R;

public interface ProductService {
    R promo(String categoryName);

    R hots(ProductHotParam param);

    R clist();

    R byCategory(ProductIdsParam param);
}
