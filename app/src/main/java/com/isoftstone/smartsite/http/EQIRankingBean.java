package com.isoftstone.smartsite.http;

import java.util.ArrayList;

/**
 * Created by gone on 2017/10/29.
 */

public class EQIRankingBean {
    private ArrayList<ArchBean> archs  = null;
    private ArrayList<ArchBean> PM2_5 =  null;
    private ArrayList<CO2> CO2 = null;
    private ArrayList<PM10>  PM10 = null;
    private ArrayList<AQI>   AQI = null;

    public ArrayList<ArchBean> getArchs() {
        return archs;
    }

    public void setArchs(ArrayList<ArchBean> archs) {
        this.archs = archs;
    }

    public ArrayList<ArchBean> getPM2_5() {
        return PM2_5;
    }

    public void setPM2_5(ArrayList<ArchBean> PM2_5) {
        this.PM2_5 = PM2_5;
    }

    public ArrayList<EQIRankingBean.CO2> getCO2() {
        return CO2;
    }

    public void setCO2(ArrayList<EQIRankingBean.CO2> CO2) {
        this.CO2 = CO2;
    }

    public ArrayList<EQIRankingBean.PM10> getPM10() {
        return PM10;
    }

    public void setPM10(ArrayList<EQIRankingBean.PM10> PM10) {
        this.PM10 = PM10;
    }

    public ArrayList<EQIRankingBean.AQI> getAQI() {
        return AQI;
    }

    public void setAQI(ArrayList<EQIRankingBean.AQI> AQI) {
        this.AQI = AQI;
    }

    //区域信息
    public static  class ArchBean {
        private  String archId = "";
        private  String archName = "";

        public String getArchId() {
            return archId;
        }

        public void setArchId(String archId) {
            this.archId = archId;
        }

        public String getArchName() {
            return archName;
        }

        public void setArchName(String archName) {
            this.archName = archName;
        }
    }

    public  static class MP2_5{
        private String archId;
        private String archName;
        private String data;
        private String color;

        public String getArchId() {
            return archId;
        }

        public void setArchId(String archId) {
            this.archId = archId;
        }

        public String getArchName() {
            return archName;
        }

        public void setArchName(String archName) {
            this.archName = archName;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static  class CO2{
        private String archId = "";
        private String archName = "";
        private  String data = "";
        private String color;

        public String getArchId() {
            return archId;
        }

        public void setArchId(String archId) {
            this.archId = archId;
        }

        public String getArchName() {
            return archName;
        }

        public void setArchName(String archName) {
            this.archName = archName;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static  class  PM10{
        private String archId = "";
        private String archName = "";
        private  String data = "";
        private String color;

        public String getArchId() {
            return archId;
        }

        public void setArchId(String archId) {
            this.archId = archId;
        }

        public String getArchName() {
            return archName;
        }

        public void setArchName(String archName) {
            this.archName = archName;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public  static  class AQI{
        private String archId = "";
        private String archName = "";
        private  String data = "";
        private String color;

        public String getArchId() {
            return archId;
        }

        public void setArchId(String archId) {
            this.archId = archId;
        }

        public String getArchName() {
            return archName;
        }

        public void setArchName(String archName) {
            this.archName = archName;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}