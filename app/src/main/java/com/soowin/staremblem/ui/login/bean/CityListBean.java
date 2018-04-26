package com.soowin.staremblem.ui.login.bean;

import com.soowin.staremblem.ui.index.bean.BaseBean;

import java.util.List;

/**
 * Created by hxt on 2018/3/28 0028.
 * 城市列表
 */

public class CityListBean extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * Code : PEK
         * Name : 北京
         * Sign : B
         * IsSelect : 1
         */

        private String Code;
        private String Name;
        private String Sign;
        private String IsSelect;
        private Boolean isSelected;

        public Boolean getSelected() {
            return isSelected;
        }

        public void setSelected(Boolean selected) {
            isSelected = selected;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getSign() {
            return Sign;
        }

        public void setSign(String Sign) {
            this.Sign = Sign;
        }

        public String getIsSelect() {
            return IsSelect;
        }

        public void setIsSelect(String IsSelect) {
            this.IsSelect = IsSelect;
        }
    }
}
