package com.hl.hl_htk.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/8.
 */

public class TreatedTgEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"orderAmount":0.01,"orderNumber":"1707035241710099","orderTime":"2017-07-03 14:33:47","orderState":12,"mark":1,"packageName":"圣保罗海鲜涮烤自助","logoUrl":"http://192.168.0.9:8080/htkApp/upload/shop/advertising/222.jpg"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * orderAmount : 0.01
         * orderNumber : 1707035241710099
         * orderTime : 2017-07-03 14:33:47
         * orderState : 12
         * mark : 1
         * packageName : 圣保罗海鲜涮烤自助
         * logoUrl : http://192.168.0.9:8080/htkApp/upload/shop/advertising/222.jpg
         */

        private double orderAmount;
        private String orderNumber;
        private String orderTime;
        private int orderState;
        private int mark;
        private String packageName;
        private String logoUrl;

        public double getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(double orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public int getOrderState() {
            return orderState;
        }

        public void setOrderState(int orderState) {
            this.orderState = orderState;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }
    }
}
