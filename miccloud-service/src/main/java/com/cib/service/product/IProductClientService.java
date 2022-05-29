package com.cib.service.product;

import com.cib.api.vo.Product;
import com.cib.service.conf.FeignClientConf;
import com.cib.service.product.factory.ProductClientServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "MICCLOUD-PROVIDER-PRODUCT",configuration = FeignClientConf.class,
                fallbackFactory = ProductClientServiceFallBack.class)
public interface IProductClientService {


    @RequestMapping("/product/get/{id}")
    public Object getProduct(@PathVariable("id") long id);

    @RequestMapping("/product/list")
    public Object listProduct();

    @RequestMapping("/product/add")
    public Object addProduct(Product product);




}
