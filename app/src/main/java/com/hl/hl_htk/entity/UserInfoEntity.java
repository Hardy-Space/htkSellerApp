package com.hl.hl_htk.entity;

/**
 * Created by Administrator on 2017/7/6.
 */

public class UserInfoEntity {


    /**
     * code : 100
     * message : 查找用户信息成功
     * data : {"token":"6be6a1ae-9f7a-4f15-aa63-a28941da8c5a","avatarImg":"http://192.168.100.6:8080/upload/app/account/appDefaultAva_img.jpg","shopState":1,"managerName":"回头客","customerServicePhone":"0532-88888"}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : 6be6a1ae-9f7a-4f15-aa63-a28941da8c5a
         * avatarImg : http://192.168.100.6:8080/upload/app/account/appDefaultAva_img.jpg
         * shopState : 1
         * managerName : 回头客
         * customerServicePhone : 0532-88888
         */

        private String token;
        private String avatarImg;
        private int shopState;   //0休息  1营业
        private String managerName;
        private String customerServicePhone;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAvatarImg() {
            return avatarImg;
        }

        public void setAvatarImg(String avatarImg) {
            this.avatarImg = avatarImg;
        }

        public int getShopState() {
            return shopState;
        }

        public void setShopState(int shopState) {
            this.shopState = shopState;
        }

        public String getManagerName() {
            return managerName;
        }

        public void setManagerName(String managerName) {
            this.managerName = managerName;
        }

        public String getCustomerServicePhone() {
            return customerServicePhone;
        }

        public void setCustomerServicePhone(String customerServicePhone) {
            this.customerServicePhone = customerServicePhone;
        }
    }
}
