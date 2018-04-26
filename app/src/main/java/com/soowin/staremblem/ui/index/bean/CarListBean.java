package com.soowin.staremblem.ui.index.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */

public class CarListBean extends BaseBean {


    /**
     * data : [{"CarModelID":"78","ProductID":"753","CarModel":"E级","CarBrand":"梅赛德斯-奔驰","CarImageUrl":"http://benz.wx.fractalist.com.cn/Content/webpage/images/lb_car1.png","CarBgImageUrl":"http://benz.wx.fractalist.com.cn/Content/webpage/images/lb4.png","CarBoxes":3,"CarGearbox":1,"CarSeats":5,"Price":480,"CostPrice":600,"ProductDesc":"限制60公里，2小时；超公里10元/公里，超小时100元/小时；","StockCount":10,"carSort":1,"LicensePlate":null,"CarDesc":{"TimeLimit":"用车时限：不超过2小时或60公里","DescList":[{"name":"原价","value":"600.00"},{"name":"活动价","value":"480.00"},{"name":"服务里程","value":"60"},{"name":"准乘人数","value":"3"}]},"RemarkList":"儿童安全座椅(6个月-4周岁);需英文客服;有老人或小朋友;需英文司机;儿童安全座椅(4-12周岁);行李多需上门;火车站接送服务;需要女司机;"},{"CarModelID":"177","ProductID":"1017","CarModel":"S400","CarBrand":"梅赛德斯-迈巴赫","CarImageUrl":"http://benz.wx.fractalist.com.cn/Content/webpage/images/lb_car3.png","CarBgImageUrl":"http://benz.wx.fractalist.com.cn/Content/webpage/images/lb3.png","CarBoxes":3,"CarGearbox":3,"CarSeats":4,"Price":1080,"CostPrice":1080,"ProductDesc":"限制60公里，2小时；超公里20元/公里，超小时200元/小时；","StockCount":0,"carSort":2,"LicensePlate":null,"CarDesc":{"TimeLimit":"用车时限：不超过2小时或60公里","DescList":[{"name":"参考价","value":"1080.00"},{"name":"服务里程","value":"60"},{"name":"准乘人数","value":"3"}]},"RemarkList":"儿童安全座椅(6个月-4周岁);需英文客服;有老人或小朋友;需英文司机;儿童安全座椅(4-12周岁);行李多需上门;火车站接送服务;需要女司机;"}]
     * _metadata : {"code":"200","message":"success"}
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class MetadataBean {
        /**
         * code : 200
         * message : success
         */

        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class DataBean {
        /**
         * CarModelID : 78
         * ProductID : 753
         * CarModel : E级
         * CarBrand : 梅赛德斯-奔驰
         * CarImageUrl : http://benz.wx.fractalist.com.cn/Content/webpage/images/lb_car1.png
         * CarBgImageUrl : http://benz.wx.fractalist.com.cn/Content/webpage/images/lb4.png
         * CarBoxes : 3
         * CarGearbox : 1
         * CarSeats : 5
         * Price : 480.0
         * CostPrice : 600.0
         * ProductDesc : 限制60公里，2小时；超公里10元/公里，超小时100元/小时；
         * StockCount : 10
         * carSort : 1
         * LicensePlate : null
         * CarDesc : {"TimeLimit":"用车时限：不超过2小时或60公里","DescList":[{"name":"原价","value":"600.00"},{"name":"活动价","value":"480.00"},{"name":"服务里程","value":"60"},{"name":"准乘人数","value":"3"}]}
         * RemarkList : 儿童安全座椅(6个月-4周岁);需英文客服;有老人或小朋友;需英文司机;儿童安全座椅(4-12周岁);行李多需上门;火车站接送服务;需要女司机;
         */

        private String CarModelID;
        private String ProductID;
        private String CarModel;
        private String CarBrand;
        private String CarImageUrl;
        private String CarBgImageUrl;
        private int CarBoxes;
        private int CarGearbox;
        private int CarSeats;
        private double Price;
        private double CostPrice;
        private String ProductDesc;
        private int StockCount;
        private int carSort;
        private Object LicensePlate;
        private CarDescBean CarDesc;
        private String RemarkList;

        public String getCarModelID() {
            return CarModelID;
        }

        public void setCarModelID(String CarModelID) {
            this.CarModelID = CarModelID;
        }

        public String getProductID() {
            return ProductID;
        }

        public void setProductID(String ProductID) {
            this.ProductID = ProductID;
        }

        public String getCarModel() {
            return CarModel;
        }

        public void setCarModel(String CarModel) {
            this.CarModel = CarModel;
        }

        public String getCarBrand() {
            return CarBrand;
        }

        public void setCarBrand(String CarBrand) {
            this.CarBrand = CarBrand;
        }

        public String getCarImageUrl() {
            return CarImageUrl;
        }

        public void setCarImageUrl(String CarImageUrl) {
            this.CarImageUrl = CarImageUrl;
        }

        public String getCarBgImageUrl() {
            return CarBgImageUrl;
        }

        public void setCarBgImageUrl(String CarBgImageUrl) {
            this.CarBgImageUrl = CarBgImageUrl;
        }

        public int getCarBoxes() {
            return CarBoxes;
        }

        public void setCarBoxes(int CarBoxes) {
            this.CarBoxes = CarBoxes;
        }

        public int getCarGearbox() {
            return CarGearbox;
        }

        public void setCarGearbox(int CarGearbox) {
            this.CarGearbox = CarGearbox;
        }

        public int getCarSeats() {
            return CarSeats;
        }

        public void setCarSeats(int CarSeats) {
            this.CarSeats = CarSeats;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public double getCostPrice() {
            return CostPrice;
        }

        public void setCostPrice(double CostPrice) {
            this.CostPrice = CostPrice;
        }

        public String getProductDesc() {
            return ProductDesc;
        }

        public void setProductDesc(String ProductDesc) {
            this.ProductDesc = ProductDesc;
        }

        public int getStockCount() {
            return StockCount;
        }

        public void setStockCount(int StockCount) {
            this.StockCount = StockCount;
        }

        public int getCarSort() {
            return carSort;
        }

        public void setCarSort(int carSort) {
            this.carSort = carSort;
        }

        public Object getLicensePlate() {
            return LicensePlate;
        }

        public void setLicensePlate(Object LicensePlate) {
            this.LicensePlate = LicensePlate;
        }

        public CarDescBean getCarDesc() {
            return CarDesc;
        }

        public void setCarDesc(CarDescBean CarDesc) {
            this.CarDesc = CarDesc;
        }

        public String getRemarkList() {
            return RemarkList;
        }

        public void setRemarkList(String RemarkList) {
            this.RemarkList = RemarkList;
        }

        public static class CarDescBean {
            /**
             * TimeLimit : 用车时限：不超过2小时或60公里
             * DescList : [{"name":"原价","value":"600.00"},{"name":"活动价","value":"480.00"},{"name":"服务里程","value":"60"},{"name":"准乘人数","value":"3"}]
             */

            private String TimeLimit;
            private List<DescListBean> DescList;

            public String getTimeLimit() {
                return TimeLimit;
            }

            public void setTimeLimit(String TimeLimit) {
                this.TimeLimit = TimeLimit;
            }

            public List<DescListBean> getDescList() {
                return DescList;
            }

            public void setDescList(List<DescListBean> DescList) {
                this.DescList = DescList;
            }

            public static class DescListBean {
                /**
                 * name : 原价
                 * value : 600.00
                 */

                private String name;
                private String value;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }
    }
}
