package com.soowin.staremblem.ui.index.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hongfu on 2018/3/20.
 * 类的作用：个人卡券列表的Bean类
 * 版本号：1.0.0
 */

public class CouponListBean extends BaseBean implements Serializable {


    /**
     * data : [{"ID":113,"TypeName":"内部代金卡","CardValue":479,"IsTrans":0,"type":4,"carddescription":"{\"Cartype\": [\"梅赛德斯-奔驰E级\"],\"City\": [\"全部城市\"],\"BeginDate\":\"2018-01-11\",\"ValidDate\":\"2019-12-31 23:59:59\",\"ServerType\": [1,2,5]}","description":{"Cartype":["梅赛德斯-奔驰E级"],"City":["全部城市"],"BeginDate":"2018-01-11T00:00:00","ValidDate":"2019-12-31T23:59:59","ServerType":[1,2,5],"Remark":null,"Subtitle":null},"RecoveryDate":0,"remark":"内部代金卡","CardSourceType":0,"CardPrice":0,"ShowHtml":"/card/_CardInfo?cardno=RWQL3QW9","InstructionsHtml":"/card/_CardIntroduce?cardno=RWQL3QW9","CardID":6297,"CardNo":"RWQL3QW9","ValidDate":"2019-12-31T23:59:59","UseDate":"9999-12-31T23:59:59.997","Content":"内部479代金卡","State":1,"OpenState":0,"SaleID":0,"LastUserID":7708,"SalesTransferTime":"2018-04-12T09:17:00.08","UsersTransferTime":"9999-12-31T23:59:59.997","ActiveID":188,"DistributorID":117,"ReceiveTime":"2018-04-12T09:17:00.08","CardType":113,"HasUseTimes":0,"IsDelete":0,"ReciveUserName":"app测试A","OldCardNo":null},{"ID":113,"TypeName":"内部代金卡","CardValue":479,"IsTrans":0,"type":4,"carddescription":"{\"Cartype\": [\"梅赛德斯-奔驰E级\"],\"City\": [\"全部城市\"],\"BeginDate\":\"2018-01-11\",\"ValidDate\":\"2019-12-31 23:59:59\",\"ServerType\": [1,2,5]}","description":{"Cartype":["梅赛德斯-奔驰E级"],"City":["全部城市"],"BeginDate":"2018-01-11T00:00:00","ValidDate":"2019-12-31T23:59:59","ServerType":[1,2,5],"Remark":null,"Subtitle":null},"RecoveryDate":0,"remark":"内部代金卡","CardSourceType":0,"CardPrice":0,"ShowHtml":"/card/_CardInfo?cardno=GX272DBM","InstructionsHtml":"/card/_CardIntroduce?cardno=GX272DBM","CardID":6296,"CardNo":"GX272DBM","ValidDate":"2019-12-31T23:59:59","UseDate":"9999-12-31T23:59:59.997","Content":"内部479代金卡","State":1,"OpenState":0,"SaleID":0,"LastUserID":7708,"SalesTransferTime":"2018-04-12T09:16:59.65","UsersTransferTime":"9999-12-31T23:59:59.997","ActiveID":188,"DistributorID":117,"ReceiveTime":"2018-04-12T09:16:59.65","CardType":113,"HasUseTimes":0,"IsDelete":0,"ReciveUserName":"app测试A","OldCardNo":null},{"ID":124,"TypeName":"内部次卡","CardValue":4,"IsTrans":0,"type":3,"carddescription":"{\"Cartype\": [\"梅赛德斯-奔驰E级\"],\"City\": [\"全部城市\"],\"BeginDate\":\"2018-01-11\",\"ValidDate\":\"2019-12-31 23:59:59\",\"ServerType\": [1,2,5]}","description":{"Cartype":["梅赛德斯-奔驰E级"],"City":["全部城市"],"BeginDate":"2018-01-11T00:00:00","ValidDate":"2019-12-31T23:59:59","ServerType":[1,2,5],"Remark":null,"Subtitle":null},"RecoveryDate":0,"remark":"内部测试次卡","CardSourceType":0,"CardPrice":0,"ShowHtml":"/card/_CardInfo?cardno=7WDSEBAB","InstructionsHtml":"/card/_CardIntroduce?cardno=7WDSEBAB","CardID":6291,"CardNo":"7WDSEBAB","ValidDate":"2019-12-31T23:59:59","UseDate":"9999-12-31T23:59:59.997","Content":"内部测试次卡","State":1,"OpenState":0,"SaleID":0,"LastUserID":7708,"SalesTransferTime":"2018-04-12T09:16:22.563","UsersTransferTime":"9999-12-31T23:59:59.997","ActiveID":201,"DistributorID":117,"ReceiveTime":"2018-04-12T09:16:22.563","CardType":124,"HasUseTimes":0,"IsDelete":0,"ReciveUserName":"app测试A","OldCardNo":null},{"ID":124,"TypeName":"内部次卡","CardValue":4,"IsTrans":0,"type":3,"carddescription":"{\"Cartype\": [\"梅赛德斯-奔驰E级\"],\"City\": [\"全部城市\"],\"BeginDate\":\"2018-01-11\",\"ValidDate\":\"2019-12-31 23:59:59\",\"ServerType\": [1,2,5]}","description":{"Cartype":["梅赛德斯-奔驰E级"],"City":["全部城市"],"BeginDate":"2018-01-11T00:00:00","ValidDate":"2019-12-31T23:59:59","ServerType":[1,2,5],"Remark":null,"Subtitle":null},"RecoveryDate":0,"remark":"内部测试次卡","CardSourceType":0,"CardPrice":0,"ShowHtml":"/card/_CardInfo?cardno=7359G6JL","InstructionsHtml":"/card/_CardIntroduce?cardno=7359G6JL","CardID":6290,"CardNo":"7359G6JL","ValidDate":"2019-12-31T23:59:59","UseDate":"9999-12-31T 23:59:59.997","Content":"内部测试次卡","State":1,"OpenState":0,"SaleID":0,"LastUserID":7708,"SalesTransferTime":"2018-04-12T09:16:22.14","UsersTransferTime":"9999-12-31T23:59:59.997","ActiveID":201,"DistributorID":117,"ReceiveTime":"2018-04-12T09:16:22.14","CardType":124,"HasUseTimes":0,"IsDelete":0,"ReciveUserName":"app测试A","OldCardNo":null}]
     * _metadata : {"code":"200","message":"我的卡劵加载成功"}
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
         * message : 我的卡劵加载成功
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

    public static class DataBean implements Serializable {
        /**
         * ID : 113
         * TypeName : 内部代金卡
         * CardValue : 479.0
         * IsTrans : 0
         * type : 4
         * carddescription : {"Cartype": ["梅赛德斯-奔驰E级"],"City": ["全部城市"],"BeginDate":"2018-01-11","ValidDate":"2019-12-31 23:59:59","ServerType": [1,2,5]}
         * description : {"Cartype":["梅赛德斯-奔驰E级"],"City":["全部城市"],"BeginDate":"2018-01-11T00:00:00","ValidDate":"2019-12-31T23:59:59","ServerType":[1,2,5],"Remark":null,"Subtitle":null}
         * RecoveryDate : 0
         * remark : 内部代金卡
         * CardSourceType : 0
         * CardPrice : 0.0
         * ShowHtml : /card/_CardInfo?cardno=RWQL3QW9
         * InstructionsHtml : /card/_CardIntroduce?cardno=RWQL3QW9
         * CardID : 6297
         * CardNo : RWQL3QW9
         * ValidDate : 2019-12-31T23:59:59
         * UseDate : 9999-12-31T23:59:59.997
         * Content : 内部479代金卡
         * State : 1
         * OpenState : 0
         * SaleID : 0
         * LastUserID : 7708
         * SalesTransferTime : 2018-04-12T09:17:00.08
         * UsersTransferTime : 9999-12-31T23:59:59.997
         * ActiveID : 188
         * DistributorID : 117
         * ReceiveTime : 2018-04-12T09:17:00.08
         * CardType : 113
         * HasUseTimes : 0
         * IsDelete : 0
         * ReciveUserName : app测试A
         * OldCardNo : null
         */

        private int ID;
        private String TypeName;
        private double CardValue;
        private int IsTrans;
        private int type;
        private String carddescription;
        private DescriptionBean description;
        private int RecoveryDate;
        private String remark;
        private int CardSourceType;
        private double CardPrice;
        private String ShowHtml;
        private String InstructionsHtml;
        private int CardID;
        private String CardNo;
        private String ValidDate;
        private String UseDate;
        private String Content;
        private int State;
        private int OpenState;
        private int SaleID;
        private int LastUserID;
        private String SalesTransferTime;
        private String UsersTransferTime;
        private int ActiveID;
        private int DistributorID;
        private String ReceiveTime;
        private int CardType;
        private int HasUseTimes;
        private int IsDelete;
        private String ReciveUserName;
        private Object OldCardNo;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String TypeName) {
            this.TypeName = TypeName;
        }

        public double getCardValue() {
            return CardValue;
        }

        public void setCardValue(double CardValue) {
            this.CardValue = CardValue;
        }

        public int getIsTrans() {
            return IsTrans;
        }

        public void setIsTrans(int IsTrans) {
            this.IsTrans = IsTrans;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCarddescription() {
            return carddescription;
        }

        public void setCarddescription(String carddescription) {
            this.carddescription = carddescription;
        }

        public DescriptionBean getDescription() {
            return description;
        }

        public void setDescription(DescriptionBean description) {
            this.description = description;
        }

        public int getRecoveryDate() {
            return RecoveryDate;
        }

        public void setRecoveryDate(int RecoveryDate) {
            this.RecoveryDate = RecoveryDate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getCardSourceType() {
            return CardSourceType;
        }

        public void setCardSourceType(int CardSourceType) {
            this.CardSourceType = CardSourceType;
        }

        public double getCardPrice() {
            return CardPrice;
        }

        public void setCardPrice(double CardPrice) {
            this.CardPrice = CardPrice;
        }

        public String getShowHtml() {
            return ShowHtml;
        }

        public void setShowHtml(String ShowHtml) {
            this.ShowHtml = ShowHtml;
        }

        public String getInstructionsHtml() {
            return InstructionsHtml;
        }

        public void setInstructionsHtml(String InstructionsHtml) {
            this.InstructionsHtml = InstructionsHtml;
        }

        public int getCardID() {
            return CardID;
        }

        public void setCardID(int CardID) {
            this.CardID = CardID;
        }

        public String getCardNo() {
            return CardNo;
        }

        public void setCardNo(String CardNo) {
            this.CardNo = CardNo;
        }

        public String getValidDate() {
            return ValidDate;
        }

        public void setValidDate(String ValidDate) {
            this.ValidDate = ValidDate;
        }

        public String getUseDate() {
            return UseDate;
        }

        public void setUseDate(String UseDate) {
            this.UseDate = UseDate;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public int getOpenState() {
            return OpenState;
        }

        public void setOpenState(int OpenState) {
            this.OpenState = OpenState;
        }

        public int getSaleID() {
            return SaleID;
        }

        public void setSaleID(int SaleID) {
            this.SaleID = SaleID;
        }

        public int getLastUserID() {
            return LastUserID;
        }

        public void setLastUserID(int LastUserID) {
            this.LastUserID = LastUserID;
        }

        public String getSalesTransferTime() {
            return SalesTransferTime;
        }

        public void setSalesTransferTime(String SalesTransferTime) {
            this.SalesTransferTime = SalesTransferTime;
        }

        public String getUsersTransferTime() {
            return UsersTransferTime;
        }

        public void setUsersTransferTime(String UsersTransferTime) {
            this.UsersTransferTime = UsersTransferTime;
        }

        public int getActiveID() {
            return ActiveID;
        }

        public void setActiveID(int ActiveID) {
            this.ActiveID = ActiveID;
        }

        public int getDistributorID() {
            return DistributorID;
        }

        public void setDistributorID(int DistributorID) {
            this.DistributorID = DistributorID;
        }

        public String getReceiveTime() {
            return ReceiveTime;
        }

        public void setReceiveTime(String ReceiveTime) {
            this.ReceiveTime = ReceiveTime;
        }

        public int getCardType() {
            return CardType;
        }

        public void setCardType(int CardType) {
            this.CardType = CardType;
        }

        public int getHasUseTimes() {
            return HasUseTimes;
        }

        public void setHasUseTimes(int HasUseTimes) {
            this.HasUseTimes = HasUseTimes;
        }

        public int getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(int IsDelete) {
            this.IsDelete = IsDelete;
        }

        public String getReciveUserName() {
            return ReciveUserName;
        }

        public void setReciveUserName(String ReciveUserName) {
            this.ReciveUserName = ReciveUserName;
        }

        public Object getOldCardNo() {
            return OldCardNo;
        }

        public void setOldCardNo(Object OldCardNo) {
            this.OldCardNo = OldCardNo;
        }

        public static class DescriptionBean implements Serializable{
            /**
             * Cartype : ["梅赛德斯-奔驰E级"]
             * City : ["全部城市"]
             * BeginDate : 2018-01-11T00:00:00
             * ValidDate : 2019-12-31T23:59:59
             * ServerType : [1,2,5]
             * Remark : null
             * Subtitle : null
             */

            private String BeginDate;
            private String ValidDate;
            private Object Remark;
            private Object Subtitle;
            private List<String> Cartype;
            private List<String> City;
            private List<Integer> ServerType;

            public String getBeginDate() {
                return BeginDate;
            }

            public void setBeginDate(String BeginDate) {
                this.BeginDate = BeginDate;
            }

            public String getValidDate() {
                return ValidDate;
            }

            public void setValidDate(String ValidDate) {
                this.ValidDate = ValidDate;
            }

            public Object getRemark() {
                return Remark;
            }

            public void setRemark(Object Remark) {
                this.Remark = Remark;
            }

            public Object getSubtitle() {
                return Subtitle;
            }

            public void setSubtitle(Object Subtitle) {
                this.Subtitle = Subtitle;
            }

            public List<String> getCartype() {
                return Cartype;
            }

            public void setCartype(List<String> Cartype) {
                this.Cartype = Cartype;
            }

            public List<String> getCity() {
                return City;
            }

            public void setCity(List<String> City) {
                this.City = City;
            }

            public List<Integer> getServerType() {
                return ServerType;
            }

            public void setServerType(List<Integer> ServerType) {
                this.ServerType = ServerType;
            }
        }
    }
}
