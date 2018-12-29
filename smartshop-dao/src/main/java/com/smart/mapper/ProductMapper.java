package com.smart.mapper;

import com.smart.pojo.Product;

import java.util.List;

public interface ProductMapper {

    List<Product> selectAllProduct(Product profuct);

    int insertProduct(Product product);
}
