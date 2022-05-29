package com.cib.product.mapper;

import com.cib.api.vo.Product;

import java.util.List;

public interface ProductMapper {

    boolean createProduct(Product product);

    public Product findById(Long id);

    public List<Product> findAll();
}
