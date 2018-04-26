package com.soowin.staremblem.ui.index.bean;

import java.io.Serializable;

/**
 * Created by hxt on 2018/3/28 0028.
 * 创建订单所用实体类
 */

public class CreateOrderBena implements Serializable {
    private int productID;
    private int carModelID;
    private int serviceType;
    private String cityCode;
    private String cityName;
    private String carBrand;
    private String serviceCar;

    public String getServiceCar() {
        return serviceCar;
    }

    public void setServiceCar(String serviceCar) {
        this.serviceCar = serviceCar;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public int getCarModelID() {
        return carModelID;
    }

    public void setCarModelID(int carModelID) {
        this.carModelID = carModelID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
