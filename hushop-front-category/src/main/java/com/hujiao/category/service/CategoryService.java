package com.hujiao.category.service;

import com.hujiao.param.ProductHotParam;
import com.hujiao.utils.R;

public interface CategoryService {
    R byName(String categoryName);

    R hotsCategory(ProductHotParam param);

    R list();
}
