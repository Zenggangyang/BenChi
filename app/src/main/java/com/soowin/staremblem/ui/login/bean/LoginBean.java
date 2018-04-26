package com.soowin.staremblem.ui.login.bean;



/**
 * Created by Administrator on 2018/3/20.
 */

    public class LoginBean {


    /**
     * _metadata : {"code":200,"message":"ok"}
     * data : {"token":"dummytoken","IsNewUser":"1"}
     */

    private MetadataBean _metadata;
    private DataBean data;

    public MetadataBean get_metadata() {
        return _metadata;
    }

    public void set_metadata(MetadataBean _metadata) {
        this._metadata = _metadata;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class MetadataBean {
        /**
         * code : 200
         * message : ok
         */

        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
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
         * token : dummytoken
         * IsNewUser : 1
         */

        private String token;
        private String IsNewUser;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getIsNewUser() {
            return IsNewUser;
        }

        public void setIsNewUser(String IsNewUser) {
            this.IsNewUser = IsNewUser;
        }
    }
}
