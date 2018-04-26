package com.soowin.staremblem.ui.index.bean;

/**
 * Created by hxt on 2018/3/21 0021.
 */

public class LoadPageHtmlBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String PageHtmlUrl;

        public String getPageHtmlUrl() {
            return PageHtmlUrl;
        }

        public void setPageHtmlUrl(String PageHtmlUrl) {
            this.PageHtmlUrl = PageHtmlUrl;
        }
    }
}
