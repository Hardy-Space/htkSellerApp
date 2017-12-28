package com.hl.hl_htk.receiver;

/**
 * Created by Administrator on 2017/11/9.
 */

public class NewOrderEvent {

    private String orderNumber;
    private int orderId;

    public NewOrderEvent(String orderNumber, int orderId) {
        this.orderNumber = orderNumber;
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public int getOrderId() {
        return orderId;
    }
}
