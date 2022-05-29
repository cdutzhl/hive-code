package com.cib.product.service;

import com.cib.api.vo.Product;

import java.util.List;

public interface IProductService {

    boolean createProduct(Product product);

    public Product findById(Long id);

    public List<Product> findAll();
}
