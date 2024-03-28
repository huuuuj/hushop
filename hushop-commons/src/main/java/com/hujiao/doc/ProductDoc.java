package com.hujiao.doc;

import com.hujiao.pojo.Category;
import com.hujiao.pojo.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用来存商品搜索数据
 */
@Data
@NoArgsConstructor
public class ProductDoc extends Product {
    /**
     * 商品名称，标题，描述的整合
     */
    private String all;

    public ProductDoc(Product product) {
        super(product.getProductId(), product.getProductName(), product.getCategoryId(),
                product.getProductTitle(), product.getProductIntro(), product.getProductPicture(),
                product.getProductPrice(), product.getProductSellingPrice(), product.getProductNum(),
                product.getProductSales());
        this.all=product.getProductName()+product.getProductTitle();
//        this.all=product.getProductName()+product.getProductTitle()+product.getProductIntro();
    }
}
