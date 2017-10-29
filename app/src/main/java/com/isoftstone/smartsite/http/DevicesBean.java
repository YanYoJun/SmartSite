package com.isoftstone.smartsite.http;

/**
 * Created by gone on 2017/10/29.
 */

public class DevicesBean {
    private  String  deviceId;
    private  String  deviceCoding;
    private  String  deviceName;
    private  int  deviceType;
    private  String  installTime;
    private  DevicesArch arch;
    private String  deviceStatus;
    private String  longitude;
    private String  latitude;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceCoding() {
        return deviceCoding;
    }

    public void setDeviceCoding(String deviceCoding) {
        this.deviceCoding = deviceCoding;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getInstallTime() {
        return installTime;
    }

    public void setInstallTime(String installTime) {
        this.installTime = installTime;
    }

    public DevicesArch getArch() {
        return arch;
    }

    public void setArch(DevicesArch arch) {
        this.arch = arch;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public static class DevicesArch{
        private String id;
        private String parentId ;
        private String  parentName;
        private String  level;
        private String  name;
        private String  number;
        private String  searchCode;
        private String  creatorId;
        private String  hasChild;
        private String  longitude;
        private String  latitude;
        private String  createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getParentName() {
            return parentName;
        }

        public void setParentName(String parentName) {
            this.parentName = parentName;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getSearchCode() {
            return searchCode;
        }

        public void setSearchCode(String searchCode) {
            this.searchCode = searchCode;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public String getHasChild() {
            return hasChild;
        }

        public void setHasChild(String hasChild) {
            this.hasChild = hasChild;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
