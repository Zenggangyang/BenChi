package com.soowin.staremblem.ui.index.bean;

/**
 * Created by hxt on 2018/3/15 0015.
 */

public class BaseBean {

    /**
     * _metadata : {"code":200,"message":"ok"}
     * data : {"token":"dummytoken"}
     */

    private MetadataBean _metadata;

    public MetadataBean get_metadata() {
        return _metadata;
    }

    public void set_metadata(MetadataBean _metadata) {
        this._metadata = _metadata;
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
}
