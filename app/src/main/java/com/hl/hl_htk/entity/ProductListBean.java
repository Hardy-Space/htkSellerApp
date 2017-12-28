package com.hl.hl_htk.entity;

/**
 * Created by Administrator on 2017/7/8.
 */

public class ProductListBean {

    private String productName;
    private int quantity;
    private double price;
    private Object productId;
    private int orderId;

    public ProductListBean(String productName, int quantity, Object productId, int orderId, double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.productId = productId;
        this.orderId = orderId;
        this.price = price;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Object getProductId() {
        return productId;
    }

    public void setProductId(Object productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
