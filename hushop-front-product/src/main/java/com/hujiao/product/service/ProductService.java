package com.hujiao.product.service;

import com.hujiao.param.ProductHotParam;
import com.hujiao.param.ProductIdsParam;
import com.hujiao.param.ProductSearchParam;
import com.hujiao.pojo.Product;
import com.hujiao.utils.R;

import java.util.List;

public interface ProductService {
    R promo(String categoryName);

    R hots(ProductHotParam param);

    R clist();

    R byCategory(ProductIdsParam param);

    R detail(Integer categoryID);

    R pictures(Integer productID);

    List<Product> allList();

    R search(ProductSearchParam param);
}
