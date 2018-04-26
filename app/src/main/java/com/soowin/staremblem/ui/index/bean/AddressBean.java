package com.soowin.staremblem.ui.index.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/22.
 */

public class AddressBean extends BaseBean{


    /**
     * data : {"status":0,"message":"ok","result":[{"name":"建国门-地铁站","location":{"lat":39.914505,"lng":116.441577},"uid":"493a61dbcd6e54eaaaf0cdde","city":"北京市","district":"朝阳区","business":"","cityid":"131"},{"name":"建国门","location":{"lat":39.914157,"lng":116.440575},"uid":"aba83f93d2004a6c0ab93a09","city":"北京市","district":"东城区","business":"","cityid":"131"},{"name":"建国门外外交公寓","location":{"lat":39.916333,"lng":116.44416},"uid":"f3c2373c3b5c2052d32dd695","city":"北京市","district":"朝阳区","business":"","cityid":"131"},{"name":"万豪酒店(建国门店)","location":{"lat":39.910364,"lng":116.440566},"uid":"7eb30e885c123adde21103ba","city":"北京市","district":"东城区","business":"","cityid":"131"},{"name":"建国门派出所","location":{"lat":39.921924,"lng":116.429797},"uid":"b148a84ce100899ef90cad22","city":"北京市","district":"东城区","business":"","cityid":"131"},{"name":"建国门南-公交车站","location":{"lat":39.910117,"lng":116.442682},"uid":"508cd1725bee726fc9c3db5e","city":"北京市","district":"朝阳区","business":"","cityid":"131"}]}
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
         * status : 0
         * message : ok
         * result : [{"name":"建国门-地铁站","location":{"lat":39.914505,"lng":116.441577},"uid":"493a61dbcd6e54eaaaf0cdde","city":"北京市","district":"朝阳区","business":"","cityid":"131"},{"name":"建国门","location":{"lat":39.914157,"lng":116.440575},"uid":"aba83f93d2004a6c0ab93a09","city":"北京市","district":"东城区","business":"","cityid":"131"},{"name":"建国门外外交公寓","location":{"lat":39.916333,"lng":116.44416},"uid":"f3c2373c3b5c2052d32dd695","city":"北京市","district":"朝阳区","business":"","cityid":"131"},{"name":"万豪酒店(建国门店)","location":{"lat":39.910364,"lng":116.440566},"uid":"7eb30e885c123adde21103ba","city":"北京市","district":"东城区","business":"","cityid":"131"},{"name":"建国门派出所","location":{"lat":39.921924,"lng":116.429797},"uid":"b148a84ce100899ef90cad22","city":"北京市","district":"东城区","business":"","cityid":"131"},{"name":"建国门南-公交车站","location":{"lat":39.910117,"lng":116.442682},"uid":"508cd1725bee726fc9c3db5e","city":"北京市","district":"朝阳区","business":"","cityid":"131"}]
         */

        private int status;
        private String message;
        private List<ResultBean> result;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * name : 建国门-地铁站
             * location : {"lat":39.914505,"lng":116.441577}
             * uid : 493a61dbcd6e54eaaaf0cdde
             * city : 北京市
             * district : 朝阳区
             * business :
             * cityid : 131
             */

            private String name;
            private LocationBean location;
            private String uid;
            private String city;
            private String district;
            private String business;
            private String cityid;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getBusiness() {
                return business;
            }

            public void setBusiness(String business) {
                this.business = business;
            }

            public String getCityid() {
                return cityid;
            }

            public void setCityid(String cityid) {
                this.cityid = cityid;
            }

            public static class LocationBean {
                /**
                 * lat : 39.914505
                 * lng : 116.441577
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }
        }
    }
}
