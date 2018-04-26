package com.soowin.staremblem.ui.index.bean;

/**
 * Created by hongfu on 2018/3/20.
 * 类的作用：个人中心的协议
 * 版本号：1.0.0
 */

public class PageHtmlBean extends BaseBean {

    /**
     * data : {"PageHtmlUrl":"http://benz.wx.fractalist.com.cn/Login/XieYi/1"}
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
         * PageHtmlUrl : http://benz.wx.fractalist.com.cn/Login/XieYi/1
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
