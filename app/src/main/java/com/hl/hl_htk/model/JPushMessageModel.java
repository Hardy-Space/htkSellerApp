package com.hl.hl_htk.model;

/**
 * Created by Administrator on 2017/11/9.
 */

public class JPushMessageModel {

    /**
     * orderNumber : 1711096955910229
     * flag : 0
     * orderId : 97
     * orderState : 1
     */

    private String orderNumber;
    private int flag;
    private int orderId;
    private int orderState;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }
}
