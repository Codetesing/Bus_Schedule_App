package com.example.mobile_project3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStreamReader;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;

@RestController
public class TrafficLightController {

    @GetMapping("getTrafficLightInfo")
    public StringBuilder getTrafficLightInfo() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/tn_pubr_public_traffic_light_api"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=DVNwYfz2bCeyEZsFkEwESH6ZvoZpDUhj%2FJ5em%2FruYkH7Fe%2FSmf48qyGZRbH2uKDbCP8M1NoY8SnBQzPJlQfdbA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("0", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*XML/JSON 여부*/
        urlBuilder.append("&" + URLEncoder.encode("ctprvnNm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시도명*/
        urlBuilder.append("&" + URLEncoder.encode("signguNm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시군구명*/
        urlBuilder.append("&" + URLEncoder.encode("roadKnd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*도로종류*/
        urlBuilder.append("&" + URLEncoder.encode("roadRouteNo","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*도로노선번호*/
        urlBuilder.append("&" + URLEncoder.encode("roadRouteNm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*도로노선명*/
        urlBuilder.append("&" + URLEncoder.encode("roadRouteDrc","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*도로노선방향*/
        urlBuilder.append("&" + URLEncoder.encode("rdnmadr","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*소재지도로명주소*/
        urlBuilder.append("&" + URLEncoder.encode("lnmadr","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*소재지지번주소*/
        urlBuilder.append("&" + URLEncoder.encode("latitude","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*위도*/
        urlBuilder.append("&" + URLEncoder.encode("longitude","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*경도*/
        urlBuilder.append("&" + URLEncoder.encode("sgngnrInstlMthd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*신호기설치방식*/
        urlBuilder.append("&" + URLEncoder.encode("roadType","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*도로형태*/
        urlBuilder.append("&" + URLEncoder.encode("priorRoadYn","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*주도로여부*/
        urlBuilder.append("&" + URLEncoder.encode("tfclghtManageNo","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*신호등관리번호*/
        urlBuilder.append("&" + URLEncoder.encode("tfclghtSe","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*신호등구분*/
        urlBuilder.append("&" + URLEncoder.encode("tfclghtColorKnd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*신호등색종류*/
        urlBuilder.append("&" + URLEncoder.encode("sgnaspMthd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*신호등화방식*/
        urlBuilder.append("&" + URLEncoder.encode("sgnaspOrdr","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*신호등화순서*/
        urlBuilder.append("&" + URLEncoder.encode("sgnaspTime","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*신호등화시간*/
        urlBuilder.append("&" + URLEncoder.encode("sotKnd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*광원종류*/
        urlBuilder.append("&" + URLEncoder.encode("signlCtrlMthd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*신호제어방식*/
        urlBuilder.append("&" + URLEncoder.encode("signlTimeMthdType","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*신호시간결정방식*/
        urlBuilder.append("&" + URLEncoder.encode("opratnYn","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*점멸등운영여부*/
        urlBuilder.append("&" + URLEncoder.encode("flashingLightOpenHhmm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*점멸등운영시작시각*/
        urlBuilder.append("&" + URLEncoder.encode("flashingLightCloseHhmm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*점멸등운영종료시각*/
        urlBuilder.append("&" + URLEncoder.encode("fnctngSgngnrYn","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*보행자작동신호기유무*/
        urlBuilder.append("&" + URLEncoder.encode("remndrIdctYn","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*잔여시간표시기유무*/
        urlBuilder.append("&" + URLEncoder.encode("sondSgngnrYn","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시각장애인용음향신호기유무*/
        urlBuilder.append("&" + URLEncoder.encode("drcbrdSn","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*도로안내표지일련번호*/
        urlBuilder.append("&" + URLEncoder.encode("institutionNm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*관리기관명*/
        urlBuilder.append("&" + URLEncoder.encode("phoneNumber","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*관리기관전화번호*/
        urlBuilder.append("&" + URLEncoder.encode("referenceDate","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*데이터기준일자*/
        urlBuilder.append("&" + URLEncoder.encode("instt_code","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*제공기관코드*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());

        return sb;
    }
}
