package com.example.demo.controller;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// serviceKey : Gva8D7gUzmoTHh8HdWjojVwEHL9r9WbBSJob74JTiIb5qUlt04y5lz%2FC4rDBV5dsazMMUxl79%2FHKf2M2Bybffg%3D%3D
// 노선(routeId) : DGB3000937000 (관음변전소1 : 937)
// 정류소(nodeId) : DGB7011010300 (경북대학교정문건너)
// 대구 도시코드 = 22
// 정류장번호 경북대학교정문앞	21752

@RestController
public class testController {

    // 노선별 버스 위치 목록 조회
    @GetMapping("/locationByStation")
    public ArrayList<Map<String, String>> busnow(@RequestParam String busnum) throws IOException, JSONException {
        String test = null;

        if(busnum.equals("937"))
            test = "DGB3000937000";

        System.out.println(test);

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusLcInfoInqireService/getRouteAcctoBusLcList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=Gva8D7gUzmoTHh8HdWjojVwEHL9r9WbBSJob74JTiIb5qUlt04y5lz%2FC4rDBV5dsazMMUxl79%2FHKf2M2Bybffg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode("22", "UTF-8")); /*도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능*/
        urlBuilder.append("&" + URLEncoder.encode("routeId","UTF-8") + "=" + URLEncoder.encode(test, "UTF-8")); /*노선ID [국토교통부(TAGO)_버스노선정보]에서 조회가능*/
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

        JSONObject jObjects = new JSONObject(sb.toString());
        JSONObject items = jObjects.getJSONObject("response").getJSONObject("body");
        ArrayList<Map<String, String>> t = new ArrayList<Map<String, String>>();

        if(items.getInt("totalCount") > 1)
        {
            JSONArray item = items.getJSONObject("items").getJSONArray("item");
            for(int i=0; i< item.length(); i++) {
                JSONObject obj = item.getJSONObject(i);
                Map<String, String> buses = new HashMap<>();
                buses.put("위도", obj.getString("gpslati"));
                buses.put("경도", obj.getString("gpslong"));
                buses.put("버스 번호", obj.getString("vehicleno").substring(5));
                t.add(buses);
            }
        } else if(items.getInt("totalCount") == 1) {
            Map<String, String> buses = new HashMap<>();
            JSONObject obj = items.getJSONObject("items").getJSONObject("item");
            buses.put("위도", obj.getString("gpslati"));
            buses.put("경도", obj.getString("gpslong"));
            buses.put("버스 번호", obj.getString("vehicleno").substring(5));
            t.add(buses);
        } else {
            System.out.println("Error");
        }

        return t;
    }

    // 노선별 버스 위치 목록 조회
    @GetMapping("/locationByStationtest")
    public String busnowt() throws IOException, JSONException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusLcInfoInqireService/getRouteAcctoBusLcList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=Gva8D7gUzmoTHh8HdWjojVwEHL9r9WbBSJob74JTiIb5qUlt04y5lz%2FC4rDBV5dsazMMUxl79%2FHKf2M2Bybffg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode("22", "UTF-8")); /*도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능*/
        urlBuilder.append("&" + URLEncoder.encode("routeId","UTF-8") + "=" + URLEncoder.encode("DGB3000937000", "UTF-8")); /*노선ID [국토교통부(TAGO)_버스노선정보]에서 조회가능*/
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

        return sb.toString();
    }
}