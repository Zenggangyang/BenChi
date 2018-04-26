package com.soowin.staremblem.ui.index.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */

public class MessageBean extends BaseBean implements Serializable {

    private List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * messageid : 11
         * Type : 2
         * UserId : 7709
         * Title : 用车提醒
         * Subtitle : 尊敬的XXX，您的订单已预约成功！
         * SubtitleShow : 尊敬的<font color='#ff0000'>XXX</font>，您的订单已预约成功！
         * SignCss : XXX
         * ShowTime : 2018-04-12T10:51:07.927
         * ShowContent : [{"name":"订单商品","value":"商务接送"},{"name":"订单号","value":"1523345107410"},{"name":"预约时间","value":"2018/4/12"},{"name":"用车地点","value":"北京市东城区建国门"},{"name":"目的地","value":"北京市丰台区大红门"},{"name":"备注","value":"感谢您的使用，如需变更或取消请在服务前4小时外通知我方"}]
         * state : 0
         * CreateTime : 2018-04-12T10:49:27.03
         * UpdateTime : 2018-04-12T10:49:27.03
         */

        private int messageid;
        private int Type;
        private int UserId;
        private String Title;
        private String Subtitle;
        private String SubtitleShow;
        private String SignCss;
        private String ShowTime;
        private String ShowContent;
        private int state;
        private String CreateTime;
        private String UpdateTime;

        public int getMessageid() {
            return messageid;
        }

        public void setMessageid(int messageid) {
            this.messageid = messageid;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getSubtitle() {
            return Subtitle;
        }

        public void setSubtitle(String Subtitle) {
            this.Subtitle = Subtitle;
        }

        public String getSubtitleShow() {
            return SubtitleShow;
        }

        public void setSubtitleShow(String SubtitleShow) {
            this.SubtitleShow = SubtitleShow;
        }

        public String getSignCss() {
            return SignCss;
        }

        public void setSignCss(String SignCss) {
            this.SignCss = SignCss;
        }

        public String getShowTime() {
            return ShowTime;
        }

        public void setShowTime(String ShowTime) {
            this.ShowTime = ShowTime;
        }

        public String getShowContent() {
            return ShowContent;
        }

        public void setShowContent(String ShowContent) {
            this.ShowContent = ShowContent;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }
    }
}
