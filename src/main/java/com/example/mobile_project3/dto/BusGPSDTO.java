package com.example.mobile_project3.dto;

public class BusGPSDTO {
    private String routenm;     // 노선번호
    private String gpslati;       // 위도 좌표
    private String gpslong;       // 경도 좌표
    private String nodeord;   // 정류소 순서
    private String nodenm;      // 정류소명
    private String nodeid;      // 정류소ID
    private String routetp;     // 노선유형
    private String vehicleno;   // 차량번호

    public BusGPSDTO(String routenm, String gpslati, String gpslong, String nodeord, String nodenm, String nodeid, String routetp, String vehicleno) {

        this.routenm = routenm;
        this.gpslati = gpslati;
        this.gpslong = gpslong;
        this.nodeord = nodeord;
        this.nodenm = nodenm;
        this.nodeid = nodeid;
        this.routetp = routetp;
        this.vehicleno = vehicleno;
    }

    public String getRoutenm() {
        return routenm;
    }

    public void setRoutenm(String routenm) {
        this.routenm = routenm;
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

    public String getNodeord() {
        return nodeord;
    }

    public void setNodeord(String nodeord) {
        this.nodeord = nodeord;
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

    public String getRoutetp() {
        return routetp;
    }

    public void setRoutetp(String routetp) {
        this.routetp = routetp;
    }

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    @Override
    public String toString() {
        return "BusGPSDTO{" +
                ", routenm='" + routenm + '\'' +
                ", gpslati='" + gpslati + '\'' +
                ", gpslong='" + gpslong + '\'' +
                ", nodeord='" + nodeord + '\'' +
                ", nodenm='" + nodenm + '\'' +
                ", nodeid='" + nodeid + '\'' +
                ", routetp='" + routetp + '\'' +
                ", vehicleno='" + vehicleno + '\'' +
                '}';
    }
}
