package com.example.mobile_project3.model;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class NodeInfo {
    private String nodenm; // 정류소이름
    private String nodeid; // 정류소ID
    private String gpslati; // 위도
    private String gpslong; // 경도

    private int duration; // 다음 정류소까지 시간

    public NodeInfo(String nodenm, String nodeid, String gpslati, String gpslong) {
        this.nodenm = nodenm;
        this.nodeid = nodeid;
        this.gpslati = gpslati;
        this.gpslong = gpslong;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getNodenm() {
        return nodenm;
    }
    public void setNodenm(String nodenm) {
        this.nodenm = nodenm;
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
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

    @Override
    public String toString() {
        return "NodeInfo{" +
                "nodenm='" + nodenm + '\'' +
                ", nodeid='" + nodeid + '\'' +
                ", gpslati='" + gpslati + '\'' +
                ", gpslong='" + gpslong + '\'' +
                '}';
    }
}
