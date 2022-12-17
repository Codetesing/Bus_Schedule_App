package com.example.demo.model;

public class RouteInfo {
    private String routenm; // 버스번호
    private String routeid; // 버스ID
    private String nodeid;  // 정류소ID
    private String nodenm;  // 정류소이름
    private String gpslati; // 위도
    private String gpslong; // 경도
    private int duration=0; // 다음 정류소까지 걸릴 시간

    public RouteInfo(String routenm, String routeid, String nodeid, String nodenm, String gpslati, String gpslong) {
        this.routenm = routenm;
        this.routeid = routeid;
        this.nodeid = nodeid;
        this.nodenm = nodenm;
        this.gpslati = gpslati;
        this.gpslong = gpslong;
    }

    public String getRoutenm() {
        return routenm;
    }

    public void setRoutenm(String routenm) {
        this.routenm = routenm;
    }

    public String getRouteid() {
        return routeid;
    }

    public void setRouteid(String routeid) {
        this.routeid = routeid;
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    public String getNodenm() {
        return nodenm;
    }

    public void setNodenm(String nodenm) {
        this.nodenm = nodenm;
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
        return "routeInfo{" +
                "routenm='" + routenm + '\'' +
                ", routeid='" + routeid + '\'' +
                ", nodeid='" + nodeid + '\'' +
                ", nodenm='" + nodenm + '\'' +
                ", gpslati='" + gpslati + '\'' +
                ", gpslong='" + gpslong + '\'' +
                '}';
    }
}
