package com.example.mobile_project3.model;

public class NodeInfo {
    private String gpslati; // 위도
    private String gpslong; // 경도
    private String nodeid; // 정류소ID

    public NodeInfo(String gpslati, String gpslong, String nodeid) {
        this.gpslati = gpslati;
        this.gpslong = gpslong;
        this.nodeid = nodeid;
    }

    public String getGpslati() {
        return gpslati;
    }

    public void setGpslati(String gpslati) {
        this.gpslati = gpslati;
    }

    public String getGpslong() {
        return gpslong;
    }

    public void setGpslong(String gpslong) {
        this.gpslong = gpslong;
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    @Override
    public String toString() {
        return "NodeInfo{" +
                "gpslati='" + gpslati + '\'' +
                ", gpslong='" + gpslong + '\'' +
                ", nodeid='" + nodeid + '\'' +
                '}';
    }
}
