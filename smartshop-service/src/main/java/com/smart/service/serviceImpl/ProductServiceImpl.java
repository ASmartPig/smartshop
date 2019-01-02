package com.smart.service.serviceImpl;

import com.smart.mapper.ProductMapper;
import com.smart.pojo.Product;
import com.smart.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductMapper productMapper;

    @Override
    public List<Product> getUserByAccount() {
        return null;
    }

    @Override
    public int addProduct(Product product) {
        return 0;
    }
}
