package com.example.mobile_project3.controller;

import com.example.mobile_project3.dto.CityCodeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStreamReader;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class BusController {

    // get GPS of a bus by route
    @GetMapping("/getBusGPS")
    public StringBuilder getbusGPS() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusLcInfoInqireService/getRouteAcctoBusLcList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Gva8D7gUzmoTHh8HdWjojVwEHL9r9WbBSJob74JTiIb5qUlt04y5lz%2FC4rDBV5dsazMMUxl79%2FHKf2M2Bybffg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("routeId","UTF-8") + "=" + URLEncoder.encode("DJB30300037", "UTF-8")); /*노선ID [국토교통부(TAGO)_버스노선정보]에서 조회가능*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode("25", "UTF-8")); /*도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능*/

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

    // get GPS of bus approaching a specific stop by route
    @GetMapping("/getSpecificStopBusGPS")
    public StringBuilder getSpecificStopBusGPS() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusLcInfoInqireService/getRouteAcctoSpcifySttnAccesBusLcInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Gva8D7gUzmoTHh8HdWjojVwEHL9r9WbBSJob74JTiIb5qUlt04y5lz%2FC4rDBV5dsazMMUxl79%2FHKf2M2Bybffg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("routeId","UTF-8") + "=" + URLEncoder.encode("DJB30300037", "UTF-8")); /*노선ID [국토교통부(TAGO)_버스노선정보]에서 조회가능*/
        urlBuilder.append("&" + URLEncoder.encode("nodeId","UTF-8") + "=" + URLEncoder.encode("DJB8007268", "UTF-8")); /*정류소ID [국토교통부(TAGO)_버스정류소정보]에서 조회가능*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode("25", "UTF-8")); /*도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능*/

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

    // get citycode
    @GetMapping("/getCityCode")
    public StringBuilder getCityCode() throws IOException, JSONException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusLcInfoInqireService/getCtyCodeList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Gva8D7gUzmoTHh8HdWjojVwEHL9r9WbBSJob74JTiIb5qUlt04y5lz%2FC4rDBV5dsazMMUxl79%2FHKf2M2Bybffg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/

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
        // System.out.println(sb.toString());

        JSONArray item = parseJson(sb);
        System.out.println("-----get item-----");
        for(int i=0; i<item.length(); i++)
        {
            JSONObject obj = item.getJSONObject(i);
            String citycode = obj.getString("citycode");
            String cityname = obj.getString("cityname");

            System.out.println("citycode: " + citycode);
            System.out.println("cityname: " + cityname);

            CityCodeDTO data = new CityCodeDTO(citycode, cityname);
        }

        return sb;
    }

    public JSONArray parseJson(StringBuilder sb) throws JSONException {
        JSONObject jObjects = new JSONObject(sb.toString());
        JSONObject items = jObjects.getJSONObject("response").getJSONObject("body").getJSONObject("items");
        JSONArray item = items.getJSONArray("item");
        // System.out.println(item.toString());
        return item;
    }
}