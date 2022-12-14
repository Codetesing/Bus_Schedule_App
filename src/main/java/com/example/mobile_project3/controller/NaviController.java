package com.example.mobile_project3.controller;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
public class NaviController {


    @GetMapping("navigation")
    public Integer navigation(@RequestParam String start, String goal, String option) throws IOException, JSONException {
        String clientId = "z17ahs6qnw";
        String clientSecret = "9ttttQgaR6XracSDpaKFdRSxweFTue2kb6QIfmAb";

        // Geocoding 개요에 나와있는 API URL 입력.
//        String apiURL = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?start=128.6146397,35.8852186&goal=128.629909,35.877557&option=trafast";	// JSON
        StringBuilder urlBuilder = new StringBuilder("https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("start","UTF-8") + "=" + URLEncoder.encode(start, "UTF-8")); /*출발지 좌표*/
        urlBuilder.append("&" + URLEncoder.encode("goal","UTF-8") + "=" + URLEncoder.encode(goal, "UTF-8")); /*도착지 좌표*/
        urlBuilder.append("&" + URLEncoder.encode("option","UTF-8") + "=" + URLEncoder.encode(option, "UTF-8")); /*옵션*/



        URL url = new URL(urlBuilder.toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-type", "application/json"); //여기 추가부분임

        // Geocoding 개요에 나와있는 요청 헤더 입력.
        con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
        con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

        // 요청 결과 확인. 정상 호출인 경우 200
        int responseCode = con.getResponseCode();
        BufferedReader rd;
        if (responseCode == 200) {
            rd = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        String inputLine;

        StringBuffer sb = new StringBuffer();

        while((inputLine = rd.readLine()) != null) {
            sb.append(inputLine);
        }
        rd.close();
        con.disconnect();
        System.out.println(sb.toString());

        JSONObject jObjects = new JSONObject(sb.toString());
        int time = 0;

        JSONArray pre_trafast = jObjects.getJSONObject("route").getJSONArray("trafast");

        JSONObject trafast = pre_trafast.getJSONObject(0);
        JSONObject summary = trafast.getJSONObject("summary");
        time = summary.getInt("duration");
//        Map<Integer, Integer> buses = new HashMap<>();
//        buses.put(summary.getInt("distance"), summary.getInt("duration"));

//        for(int i=0; i< pre_trafast.length(); i++)
//        {
//            JSONObject obj = pre_trafast.getJSONObject(i);
//            System.out.println(obj.getJSONObject("summary"));
//        }


        System.out.println(time);
        return time;


//        return sb.toString(); // 일단임시
    }
}
