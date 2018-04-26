package com.soowin.staremblem.ui.index.bean;

/**
 * Created by hongfu on 2018/3/30.
 * 类的作用：创建订单生成的Bean类
 * 版本号：1.0.0
 */

public class CreateOrderBean extends BaseBean {

    /**
     * data : {"OrderID":"70001","Price":480}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * OrderID : 70001
         * Price : 480
         */

        private String OrderID;
        private int Price;

        public String getOrderID() {
            return OrderID;
        }

        public void setOrderID(String OrderID) {
            this.OrderID = OrderID;
        }

        public int getPrice() {
            return Price;
        }

        public void setPrice(int Price) {
            this.Price = Price;
        }
    }
}
