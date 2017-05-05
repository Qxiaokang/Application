package com.example.aozun.testapplication.bean;

import java.util.List;

/**
 * Created by Admin on 2017/3/10.
 */
public class Weather{

    /**
     * status : 1
     * data : {"province":"江苏省","cross_list":[],"code":"1","tel":"0512","cityadcode":"320500","areacode":"0512","timestamp":"1489111917.69","sea_area":{},"pos":"在政协陆家镇工作委员会附近, 在绿溪路旁边, 靠近联谊路--绿溪路路口","road_list":[],"result":"true","message":"Successful.","desc":"江苏省,苏州市,昆山市","city":"苏州市","districtadcode":"320583","district":"昆山市","country":"中国","provinceadcode":"320000","version":"2.0-3.0.7068.1520","adcode":"320583","poi_list":[]}
     */

    private String status;
    private DataBean data;

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public DataBean getData(){
        return data;
    }

    public void setData(DataBean data){
        this.data = data;
    }

    public static class DataBean{
        /**
         * province : 江苏省
         * cross_list : []
         * code : 1
         * tel : 0512
         * cityadcode : 320500
         * areacode : 0512
         * timestamp : 1489111917.69
         * sea_area : {}
         * pos : 在政协陆家镇工作委员会附近, 在绿溪路旁边, 靠近联谊路--绿溪路路口
         * road_list : []
         * result : true
         * message : Successful.
         * desc : 江苏省,苏州市,昆山市
         * city : 苏州市
         * districtadcode : 320583
         * district : 昆山市
         * country : 中国
         * provinceadcode : 320000
         * version : 2.0-3.0.7068.1520
         * adcode : 320583
         * poi_list : []
         */

        private String province;
        private String code;
        private String tel;
        private String cityadcode;
        private String areacode;
        private String timestamp;
        private SeaAreaBean sea_area;
        private String pos;
        private String result;
        private String message;
        private String desc;
        private String city;
        private String districtadcode;
        private String district;
        private String country;
        private String provinceadcode;
        private String version;
        private String adcode;
        private List<?> cross_list;
        private List<?> road_list;
        private List<?> poi_list;

        public String getProvince(){
            return province;
        }

        public void setProvince(String province){
            this.province = province;
        }

        public String getCode(){
            return code;
        }

        public void setCode(String code){
            this.code = code;
        }

        public String getTel(){
            return tel;
        }

        public void setTel(String tel){
            this.tel = tel;
        }

        public String getCityadcode(){
            return cityadcode;
        }

        public void setCityadcode(String cityadcode){
            this.cityadcode = cityadcode;
        }

        public String getAreacode(){
            return areacode;
        }

        public void setAreacode(String areacode){
            this.areacode = areacode;
        }

        public String getTimestamp(){
            return timestamp;
        }

        public void setTimestamp(String timestamp){
            this.timestamp = timestamp;
        }

        public SeaAreaBean getSea_area(){
            return sea_area;
        }

        public void setSea_area(SeaAreaBean sea_area){
            this.sea_area = sea_area;
        }

        public String getPos(){
            return pos;
        }

        public void setPos(String pos){
            this.pos = pos;
        }

        public String getResult(){
            return result;
        }

        public void setResult(String result){
            this.result = result;
        }

        public String getMessage(){
            return message;
        }

        public void setMessage(String message){
            this.message = message;
        }

        public String getDesc(){
            return desc;
        }

        public void setDesc(String desc){
            this.desc = desc;
        }

        public String getCity(){
            return city;
        }

        public void setCity(String city){
            this.city = city;
        }

        public String getDistrictadcode(){
            return districtadcode;
        }

        public void setDistrictadcode(String districtadcode){
            this.districtadcode = districtadcode;
        }

        public String getDistrict(){
            return district;
        }

        public void setDistrict(String district){
            this.district = district;
        }

        public String getCountry(){
            return country;
        }

        public void setCountry(String country){
            this.country = country;
        }

        public String getProvinceadcode(){
            return provinceadcode;
        }

        public void setProvinceadcode(String provinceadcode){
            this.provinceadcode = provinceadcode;
        }

        public String getVersion(){
            return version;
        }

        public void setVersion(String version){
            this.version = version;
        }

        public String getAdcode(){
            return adcode;
        }

        public void setAdcode(String adcode){
            this.adcode = adcode;
        }

        public List<?> getCross_list(){
            return cross_list;
        }

        public void setCross_list(List<?> cross_list){
            this.cross_list = cross_list;
        }

        public List<?> getRoad_list(){
            return road_list;
        }

        public void setRoad_list(List<?> road_list){
            this.road_list = road_list;
        }

        public List<?> getPoi_list(){
            return poi_list;
        }

        public void setPoi_list(List<?> poi_list){
            this.poi_list = poi_list;
        }

        public static class SeaAreaBean{
        }
    }
}
