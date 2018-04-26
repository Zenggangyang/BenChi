package com.soowin.staremblem.ui.index.bean;

/**
 * Created by hxt on 2018/4/17 0017.
 */

public class OrderEvaluationIndexBean extends BaseBean {

    /**
     * data : {"PageHtmlUrl":"http://benz.wx.fractalist.com.cn/Order/OrderEvaluationIndex?OrderId=70001"}
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
         * PageHtmlUrl : http://benz.wx.fractalist.com.cn/Order/OrderEvaluationIndex?OrderId=70001
         */

        private String PageHtmlUrl;

        public String getPageHtmlUrl() {
            return PageHtmlUrl;
        }

        public void setPageHtmlUrl(String PageHtmlUrl) {
            this.PageHtmlUrl = PageHtmlUrl;
        }
    }
}
