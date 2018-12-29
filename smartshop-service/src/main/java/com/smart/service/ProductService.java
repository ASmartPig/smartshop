package com.smart.service;

import com.smart.pojo.Product;

import java.util.List;

public interface ProductService {
    /**
     * 获取所有的商品列表
     * @return
     */
    List<Product> getUserByAccount();

    /**
     * 添加商品
     * @param product
     * @return
     */
    int addProduct(Product product);
}
