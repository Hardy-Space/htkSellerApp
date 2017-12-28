package com.hl.hl_htk.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/10.
 */

public class UnAllEntity {


    /**
     * code : 100
     * message : 成功
     * data : [{"orderAmount":0.01,"orderNumber":"1707035241710099","orderTime":"2017-07-03 14:33:47","orderState":12,"mark":1,"packageName":"圣保罗海鲜涮烤自助","logoUrl":"http://192.168.0.9:8080/htkApp/upload/shop/advertising/222.jpg","phone":null},{"orderAmount":14,"orderNumber":"1706305943410091","orderTime":"2017-06-30 16:30:42","orderState":4,"mark":0,"receiptName":"李四","receivingCall":"18660706075","shippingAddress":"山东省青岛市市北区延吉路卓越大厦880","longitude":120.376081,"latitude":36.081687,"deliveryTime":" 12:00","shipperName":"林梅梅","deliveryPhone":"18888888888","productList":[{"productName":"果盘1","quantity":1,"price":4,"productId":null,"orderId":14},{"productName":"果盘","quantity":1,"price":5,"productId":null,"orderId":14},{"productName":"甜筒1","quantity":1,"price":3,"productId":null,"orderId":14},{"productName":"甜筒","quantity":1,"price":2,"productId":null,"orderId":14}]},{"orderAmount":0.01,"orderNumber":"1706305082210032","orderTime":"2017-06-30 14:07:13","orderState":4,"mark":0,"receiptName":"李四","receivingCall":"18660706075","shippingAddress":"山东省青岛市市北区延吉路卓越大厦880","longitude":120.376081,"latitude":36.081687,"deliveryTime":" 12:00","shipperName":"林梅梅","deliveryPhone":"18888888888","productList":[{"productName":"果盘1","quantity":1,"price":4,"productId":null,"orderId":12},{"productName":"果盘","quantity":1,"price":5,"productId":null,"orderId":12}]},{"orderAmount":0.01,"orderNumber":"1706296248010033","orderTime":"2017-06-29 17:21:30","orderState":4,"mark":0,"receiptName":"龙威陶","receivingCall":"18766242033","shippingAddress":"山东省青岛市东仲花园","longitude":120.376081,"latitude":36.081687,"deliveryTime":" 12:00","shipperName":"林梅梅","deliveryPhone":"18888888888","productList":[{"productName":"甜筒","quantity":1,"price":2,"productId":null,"orderId":11},{"productName":"果盘1","quantity":1,"price":4,"productId":null,"orderId":11}]},{"orderAmount":0.01,"orderNumber":"1706296226510125","orderTime":"2017-06-29 17:17:53","orderState":4,"mark":0,"receiptName":"龙威陶","receivingCall":"18766242033","shippingAddress":"山东省青岛市东仲花园","longitude":120.376081,"latitude":36.081687,"deliveryTime":" 12:00","shipperName":"林梅梅","deliveryPhone":"18888888888","productList":[{"productName":"果盘1","quantity":1,"price":4,"productId":null,"orderId":10},{"productName":"果盘","quantity":1,"price":5,"productId":null,"orderId":10}]}]
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
         * phone : null
         * receiptName : 李四
         * receivingCall : 18660706075
         * shippingAddress : 山东省青岛市市北区延吉路卓越大厦880
         * longitude : 120.376081
         * latitude : 36.081687
         * deliveryTime :  12:00
         * shipperName : 林梅梅
         * deliveryPhone : 18888888888
         * productList : [{"productName":"果盘1","quantity":1,"price":4,"productId":null,"orderId":14},{"productName":"果盘","quantity":1,"price":5,"productId":null,"orderId":14},{"productName":"甜筒1","quantity":1,"price":3,"productId":null,"orderId":14},{"productName":"甜筒","quantity":1,"price":2,"productId":null,"orderId":14}]
         */

        private double orderAmount;
        private String orderNumber;
        private String orderTime;
        private int orderState;
        private int mark;
        private String packageName;
        private String logoUrl;
        private Object phone;
        private String receiptName;
        private String receivingCall;
        private String shippingAddress;
        private double longitude;
        private double latitude;
        private String deliveryTime;
        private String shipperName;
        private String deliveryPhone;
        private List<ProductListBean> productList;

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

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public String getReceiptName() {
            return receiptName;
        }

        public void setReceiptName(String receiptName) {
            this.receiptName = receiptName;
        }

        public String getReceivingCall() {
            return receivingCall;
        }

        public void setReceivingCall(String receivingCall) {
            this.receivingCall = receivingCall;
        }

        public String getShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getShipperName() {
            return shipperName;
        }

        public void setShipperName(String shipperName) {
            this.shipperName = shipperName;
        }

        public String getDeliveryPhone() {
            return deliveryPhone;
        }

        public void setDeliveryPhone(String deliveryPhone) {
            this.deliveryPhone = deliveryPhone;
        }

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

        public static class ProductListBean {
            /**
             * productName : 果盘1
             * quantity : 1
             * price : 4.0
             * productId : null
             * orderId : 14
             */

            private String productName;
            private int quantity;
            private double price;
            private Object productId;
            private int orderId;

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
    }
}
