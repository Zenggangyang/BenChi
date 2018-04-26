package com.soowin.staremblem.ui.index.bean;

/**
 * Created by hongfu on 2018/4/18.
 * 类的作用：
 * 版本号：1.0.0
 */

public class PriceDetails {
    private int  type;
    private  String price;
    private String payNo;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }
}
