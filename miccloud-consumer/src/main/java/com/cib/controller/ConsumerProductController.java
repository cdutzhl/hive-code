package com.cib.controller;

import com.cib.api.vo.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerProductController {

    Logger logger = LoggerFactory.getLogger(ConsumerProductController.class);

    private static  String PRODUCT_GET_URL="http://MICCLOUD-PROVIDER-PRODUCT/product/get/";
    private static  String PRODUCT_ADD_URL="http://MICCLOUD-PROVIDER-PRODUCT/product/add/";
    private static  String PRODUCT_LIST_URL="http://MICCLOUD-PROVIDER-PRODUCT/product/list/";
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private HttpHeaders httpHeaders;
//    @Resource
//    private DiscoveryClient  client;
    @Resource
    private LoadBalancerClient balancerClient;

    @RequestMapping("/product/get")
    public Object getProduct(long id){
        return restTemplate.exchange(PRODUCT_GET_URL+id, HttpMethod.GET,new HttpEntity<>(httpHeaders),Product.class);
    }
    @RequestMapping("/product/list")
    public Object listProduct(){
        ServiceInstance serviceInstance = balancerClient.choose("MICCLOUD-PROVIDER-PRODUCT");
        logger.info("【***ServiceInstance***】host={} 、port={}、serviceId={}",
                serviceInstance.getHost(),serviceInstance.getPort(),serviceInstance.getServiceId());
        return restTemplate.exchange(PRODUCT_LIST_URL,HttpMethod.GET,new HttpEntity<>(httpHeaders),List.class).getBody();
    }

    @RequestMapping("/product/add")
    public Object addProduct(Product product){
        return restTemplate.exchange(PRODUCT_ADD_URL,HttpMethod.POST,new HttpEntity<>(product,httpHeaders),Boolean.class).getBody();
    }
//    @RequestMapping("/discovery")
//    public Object discovery(){
//        return this.client;
//    }


}
