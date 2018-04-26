package com.soowin.staremblem.ui.index.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hongfu on 2018/3/20.
 * 类的作用：
 * 版本号：1.0.0
 */

public class UserInfoBean extends BaseBean implements Serializable{


    /**
     * data : {"Id":6,"UserID":7708,"Mobile":"15532650304","HeadImg":null,"HeadImgShow":"","AccessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJBcHBWZXJzaW9uIjpudWxsLCJTeXN0ZW1UZXh0IjpudWxsLCJEZXZpY2VOYW1lIjpudWxsLCJEZXZpY2VJZCI6bnVsbCwiZXhwIjoxNTI0MDE0MzUzLCJtb2JpbGUiOiIxNTUzMjY1MDMwNCJ9.mWXY3bers2lcRzqorW5yHxEmve_kydD1hgw47D-6IJM","TokenLimit":1.524014353E9,"Secret":"c753c5aa23ecdbb0a9c905bce8a98d94","Signature":null,"SourceChannel":2,"CreateTime":"2018-03-12T17:19:13.437","UpdateTime":"2018-04-13T09:19:06.333","IsDelete":0,"Identity":null,"Name":"15532650304","Password":null,"Balance":5000,"LastLoginTime":"2018-03-12T17:19:13.437","RoleID":0,"ActiveState":1,"OpenID":"oSr-gt-d45qyhcsUQfhZUOPTo7Gg","RealName":"app测试A","NickName":"落叶易逝","avatar":"http://thirdwx.qlogo.cn/mmopen/5EHudDmwXOde0tOEGjbKwf2ezGFLSu9pge0JtJlaKLmRRElpMy3p224xK1GZatB4LmdL7KWibiaLFyXmHN7zjbb7HliaKqFpf2W/132","ServiceTel":"4008106880","RoleList":"1,6","Sex":1,"UserRoleList":[1,6],"Hassync":1,"UserFlag":"1","Remark":null,"VVIPAttribute":null,"vvipSign":null}
     * _metadata : {"code":"200","message":"success"}
     */

    private DataBean data;



    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }



    public static class DataBean implements Serializable{
        /**
         * Id : 6
         * UserID : 7708
         * Mobile : 15532650304
         * HeadImg : null
         * HeadImgShow :
         * AccessToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJBcHBWZXJzaW9uIjpudWxsLCJTeXN0ZW1UZXh0IjpudWxsLCJEZXZpY2VOYW1lIjpudWxsLCJEZXZpY2VJZCI6bnVsbCwiZXhwIjoxNTI0MDE0MzUzLCJtb2JpbGUiOiIxNTUzMjY1MDMwNCJ9.mWXY3bers2lcRzqorW5yHxEmve_kydD1hgw47D-6IJM
         * TokenLimit : 1.524014353E9
         * Secret : c753c5aa23ecdbb0a9c905bce8a98d94
         * Signature : null
         * SourceChannel : 2
         * CreateTime : 2018-03-12T17:19:13.437
         * UpdateTime : 2018-04-13T09:19:06.333
         * IsDelete : 0
         * Identity : null
         * Name : 15532650304
         * Password : null
         * Balance : 5000.0
         * LastLoginTime : 2018-03-12T17:19:13.437
         * RoleID : 0
         * ActiveState : 1
         * OpenID : oSr-gt-d45qyhcsUQfhZUOPTo7Gg
         * RealName : app测试A
         * NickName : 落叶易逝
         * avatar : http://thirdwx.qlogo.cn/mmopen/5EHudDmwXOde0tOEGjbKwf2ezGFLSu9pge0JtJlaKLmRRElpMy3p224xK1GZatB4LmdL7KWibiaLFyXmHN7zjbb7HliaKqFpf2W/132
         * ServiceTel : 4008106880
         * RoleList : 1,6
         * Sex : 1
         * UserRoleList : [1,6]
         * Hassync : 1
         * UserFlag : 1
         * Remark : null
         * VVIPAttribute : null
         * vvipSign : null
         */

        private int Id;
        private int UserID;
        private String Mobile;
        private Object HeadImg;
        private String HeadImgShow;
        private String AccessToken;
        private double TokenLimit;
        private String Secret;
        private Object Signature;
        private int SourceChannel;
        private String CreateTime;
        private String UpdateTime;
        private int IsDelete;
        private Object Identity;
        private String Name;
        private Object Password;
        private double Balance;
        private String LastLoginTime;
        private int RoleID;
        private int ActiveState;
        private String OpenID;
        private String RealName;
        private String NickName;
        private String avatar;
        private String ServiceTel;
        private String RoleList;
        private int Sex;
        private int Hassync;
        private String UserFlag;
        private Object Remark;
        private Object VVIPAttribute;
        private Object vvipSign;
        private List<Integer> UserRoleList;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public Object getHeadImg() {
            return HeadImg;
        }

        public void setHeadImg(Object HeadImg) {
            this.HeadImg = HeadImg;
        }

        public String getHeadImgShow() {
            return HeadImgShow;
        }

        public void setHeadImgShow(String HeadImgShow) {
            this.HeadImgShow = HeadImgShow;
        }

        public String getAccessToken() {
            return AccessToken;
        }

        public void setAccessToken(String AccessToken) {
            this.AccessToken = AccessToken;
        }

        public double getTokenLimit() {
            return TokenLimit;
        }

        public void setTokenLimit(double TokenLimit) {
            this.TokenLimit = TokenLimit;
        }

        public String getSecret() {
            return Secret;
        }

        public void setSecret(String Secret) {
            this.Secret = Secret;
        }

        public Object getSignature() {
            return Signature;
        }

        public void setSignature(Object Signature) {
            this.Signature = Signature;
        }

        public int getSourceChannel() {
            return SourceChannel;
        }

        public void setSourceChannel(int SourceChannel) {
            this.SourceChannel = SourceChannel;
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

        public int getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(int IsDelete) {
            this.IsDelete = IsDelete;
        }

        public Object getIdentity() {
            return Identity;
        }

        public void setIdentity(Object Identity) {
            this.Identity = Identity;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public Object getPassword() {
            return Password;
        }

        public void setPassword(Object Password) {
            this.Password = Password;
        }

        public double getBalance() {
            return Balance;
        }

        public void setBalance(double Balance) {
            this.Balance = Balance;
        }

        public String getLastLoginTime() {
            return LastLoginTime;
        }

        public void setLastLoginTime(String LastLoginTime) {
            this.LastLoginTime = LastLoginTime;
        }

        public int getRoleID() {
            return RoleID;
        }

        public void setRoleID(int RoleID) {
            this.RoleID = RoleID;
        }

        public int getActiveState() {
            return ActiveState;
        }

        public void setActiveState(int ActiveState) {
            this.ActiveState = ActiveState;
        }

        public String getOpenID() {
            return OpenID;
        }

        public void setOpenID(String OpenID) {
            this.OpenID = OpenID;
        }

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getServiceTel() {
            return ServiceTel;
        }

        public void setServiceTel(String ServiceTel) {
            this.ServiceTel = ServiceTel;
        }

        public String getRoleList() {
            return RoleList;
        }

        public void setRoleList(String RoleList) {
            this.RoleList = RoleList;
        }

        public int getSex() {
            return Sex;
        }

        public void setSex(int Sex) {
            this.Sex = Sex;
        }

        public int getHassync() {
            return Hassync;
        }

        public void setHassync(int Hassync) {
            this.Hassync = Hassync;
        }

        public String getUserFlag() {
            return UserFlag;
        }

        public void setUserFlag(String UserFlag) {
            this.UserFlag = UserFlag;
        }

        public Object getRemark() {
            return Remark;
        }

        public void setRemark(Object Remark) {
            this.Remark = Remark;
        }

        public Object getVVIPAttribute() {
            return VVIPAttribute;
        }

        public void setVVIPAttribute(Object VVIPAttribute) {
            this.VVIPAttribute = VVIPAttribute;
        }

        public Object getVvipSign() {
            return vvipSign;
        }

        public void setVvipSign(Object vvipSign) {
            this.vvipSign = vvipSign;
        }

        public List<Integer> getUserRoleList() {
            return UserRoleList;
        }

        public void setUserRoleList(List<Integer> UserRoleList) {
            this.UserRoleList = UserRoleList;
        }
    }

}
