package com.example.mobile_project3.dto;

public class SCSBusGPSDTO {
    private String routenm;     // 노선번호
    private String gpslati;       // 위도 좌표
    private String gpslong;       // 경도 좌표
    private String stopnm;      // 정류소명
    private String routetp;     // 노선유형

    public SCSBusGPSDTO(String routenm, String gpslati, String gpslong, String stopnm, String routetp) {
        this.routenm = routenm;
        this.gpslati = gpslati;
        this.gpslong = gpslong;
        this.stopnm = stopnm;
        this.routetp = routetp;
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

    public String getStopnm() {
        return stopnm;
    }

    public void setStopnm(String stopnm) {
        this.stopnm = stopnm;
    }

    public String getRoutetp() {
        return routetp;
    }

    public void setRoutetp(String routetp) {
        this.routetp = routetp;
    }

    @Override
    public String toString() {
        return "SCSBusGPSDTO{" +
                ", routenm='" + routenm + '\'' +
                ", gpslati='" + gpslati + '\'' +
                ", gpslong='" + gpslong + '\'' +
                ", stopnm='" + stopnm + '\'' +
                ", routetp='" + routetp + '\'' +
                '}';
    }
}
