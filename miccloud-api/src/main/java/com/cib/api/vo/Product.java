package com.cib.api.vo;

import java.io.Serializable;

public class Product implements Serializable {
    /**
     * 产品编号
     */
    private long productId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品描述
     */
    private String productDesc;

    public long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    @Override
    public String toString() {
        return "Product{"+
                "productId="+productId+
                ",productName="+productName+
                ",productDesc="+productDesc
                +"}";
    }
}
