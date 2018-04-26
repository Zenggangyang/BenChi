package com.soowin.staremblem.ui.index.bean;

/**
 * Created by hxt on 2018/3/21 0021.
 */

public class SourceupdateBean extends BaseBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private int isupdate;

        public int getIsupdate() {
            return isupdate;
        }

        public void setIsupdate(int isupdate) {
            this.isupdate = isupdate;
        }
    }
}
