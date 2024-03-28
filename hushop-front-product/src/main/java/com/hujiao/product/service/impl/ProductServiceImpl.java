package com.hujiao.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hujiao.clients.CategoryClient;
import com.hujiao.clients.SearchClient;
import com.hujiao.param.ProductHotParam;
import com.hujiao.param.ProductIdsParam;
import com.hujiao.param.ProductSearchParam;
import com.hujiao.pojo.Picture;
import com.hujiao.pojo.Product;
import com.hujiao.product.mapper.PictureMapper;
import com.hujiao.product.mapper.ProductMapper;
import com.hujiao.product.service.ProductService;
import com.hujiao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private SearchClient searchClient;

    /**
     * 查询分类下热门商品
     *
     * @param categoryName
     * @return
     */
    @Override
    public R promo(String categoryName) {
        R r = categoryClient.byName(categoryName);
        if (r.getCode().equals(R.FAIL_CODE)) {
            return r;
        }
        LinkedHashMap<String, Object> data = (LinkedHashMap<String, Object>) r.getData();
        Integer categoryId = (Integer) data.get("category_id");
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("category_id", categoryId);
        productQueryWrapper.orderByDesc("product_sales");

        IPage<Product> page = new Page<>(1, 7);
        page = productMapper.selectPage(page, productQueryWrapper);
        List<Product> records = page.getRecords();

        return R.ok("查询成功", records);
    }

    @Override
    public R hots(ProductHotParam param) {
        R r = categoryClient.hotsCategory(param);
        if (r.getCode().equals(R.FAIL_CODE)) {
            return r;
        }
        List<Object> ids = (List<Object>) r.getData();
        QueryWrapper<Product> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.in("category_id", ids);
        objectQueryWrapper.orderByDesc("product_sales");
        IPage<Product> page = new Page<>(1, 7);
        page = productMapper.selectPage(page, objectQueryWrapper);
        List<Product> records = page.getRecords();
        return R.ok("查询成功", records);
    }

    @Override
    public R clist() {
        R list = categoryClient.list();
        return list;
    }

    @Override
    public R byCategory(ProductIdsParam param) {
        List<Integer> categoryID = param.getCategoryID();
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        if (!categoryID.isEmpty()) {
            productQueryWrapper.in("category_id", categoryID);
        }
        IPage<Product> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        page = productMapper.selectPage(page, productQueryWrapper);
        List<Product> records = page.getRecords();
        long total = page.getTotal();
        return R.ok("success!",records,total);
    }

    @Override
    public R detail(Integer categoryID) {
        Product product = productMapper.selectById(categoryID);
        return R.ok(product);
    }

    @Override
    public R pictures(Integer productID) {
        QueryWrapper<Picture> pictureQueryWrapper = new QueryWrapper<>();
        pictureQueryWrapper.eq("product_id",productID);
        List<Picture> pictures = pictureMapper.selectList(pictureQueryWrapper);
        return R.ok(pictures);
    }

    /**
     * 搜索全部商品数据进行es同步
     * @return
     */
    @Override
    public List<Product> allList() {
        return productMapper.selectList(null);
    }

    @Override
    public R search(ProductSearchParam param) {
        return searchClient.searchProduct(param);
    }
}
