package com.soowin.staremblem.ui.index.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hxt on 2018/4/2 0002.
 */

public class FlinghtInforBean extends BaseBean {


    /**
     * data : [{"FlightNo":null,"DepPortCode":"SHA","DepPortName":"上海虹桥国际机场","DepCityCode":"SHA","DepCityName":"上海","DepTerminal":"T2","DepDateTime":"2018-04-14T08:00:00","ArrPortCode":"PEK","ArrPortName":"北京首都国际机场","ArrCityCode":"PEK","ArrCityName":"北京","ArrTerminal":"T2","ArrDateTime":"2018-04-14T10:15:00"}]
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
         * FlightNo : null
         * DepPortCode : SHA
         * DepPortName : 上海虹桥国际机场
         * DepCityCode : SHA
         * DepCityName : 上海
         * DepTerminal : T2
         * DepDateTime : 2018-04-14T08:00:00
         * ArrPortCode : PEK
         * ArrPortName : 北京首都国际机场
         * ArrCityCode : PEK
         * ArrCityName : 北京
         * ArrTerminal : T2
         * ArrDateTime : 2018-04-14T10:15:00
         */

        private Object FlightNo;
        private String DepPortCode;
        private String DepPortName;
        private String DepCityCode;
        private String DepCityName;
        private String DepTerminal;
        private String DepDateTime;
        private String ArrPortCode;
        private String ArrPortName;
        private String ArrCityCode;
        private String ArrCityName;
        private String ArrTerminal;
        private String ArrDateTime;

        public Object getFlightNo() {
            return FlightNo;
        }

        public void setFlightNo(Object FlightNo) {
            this.FlightNo = FlightNo;
        }

        public String getDepPortCode() {
            return DepPortCode;
        }

        public void setDepPortCode(String DepPortCode) {
            this.DepPortCode = DepPortCode;
        }

        public String getDepPortName() {
            return DepPortName;
        }

        public void setDepPortName(String DepPortName) {
            this.DepPortName = DepPortName;
        }

        public String getDepCityCode() {
            return DepCityCode;
        }

        public void setDepCityCode(String DepCityCode) {
            this.DepCityCode = DepCityCode;
        }

        public String getDepCityName() {
            return DepCityName;
        }

        public void setDepCityName(String DepCityName) {
            this.DepCityName = DepCityName;
        }

        public String getDepTerminal() {
            return DepTerminal;
        }

        public void setDepTerminal(String DepTerminal) {
            this.DepTerminal = DepTerminal;
        }

        public String getDepDateTime() {
            return DepDateTime;
        }

        public void setDepDateTime(String DepDateTime) {
            this.DepDateTime = DepDateTime;
        }

        public String getArrPortCode() {
            return ArrPortCode;
        }

        public void setArrPortCode(String ArrPortCode) {
            this.ArrPortCode = ArrPortCode;
        }

        public String getArrPortName() {
            return ArrPortName;
        }

        public void setArrPortName(String ArrPortName) {
            this.ArrPortName = ArrPortName;
        }

        public String getArrCityCode() {
            return ArrCityCode;
        }

        public void setArrCityCode(String ArrCityCode) {
            this.ArrCityCode = ArrCityCode;
        }

        public String getArrCityName() {
            return ArrCityName;
        }

        public void setArrCityName(String ArrCityName) {
            this.ArrCityName = ArrCityName;
        }

        public String getArrTerminal() {
            return ArrTerminal;
        }

        public void setArrTerminal(String ArrTerminal) {
            this.ArrTerminal = ArrTerminal;
        }

        public String getArrDateTime() {
            return ArrDateTime;
        }

        public void setArrDateTime(String ArrDateTime) {
            this.ArrDateTime = ArrDateTime;
        }
    }
}
