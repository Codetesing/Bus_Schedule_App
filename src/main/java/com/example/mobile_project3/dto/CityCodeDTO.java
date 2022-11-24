package com.example.mobile_project3.dto;

public class CityCodeDTO {
    private String citycode;    // 도시코드
    private String cityname;    // 도시명

    public CityCodeDTO () {}
    public CityCodeDTO( String citycode, String cityname) {
        this.citycode = citycode;
        this.cityname = cityname;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    @Override
    public String toString() {
        return "CityCodeDTO{" +
                ", citycode='" + citycode + '\'' +
                ", cityname='" + cityname + '\'' +
                '}';
    }
}
