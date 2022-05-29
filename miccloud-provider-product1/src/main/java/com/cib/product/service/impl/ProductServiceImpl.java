package com.cib.product.service.impl;

import com.cib.api.vo.Product;
import com.cib.product.mapper.ProductMapper;
import com.cib.product.service.IProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Resource
    private ProductMapper productMapper;

    @Override
    public boolean createProduct(Product product) {
        return productMapper.createProduct(product);
    }

    @Override
    public Product findById(Long id) {
        return productMapper.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productMapper.findAll();
    }
}
