package com.example.mobile_project3.controller;

import com.example.mobile_project3.model.routeID;
import com.example.mobile_project3.model.NodeInfo;
import com.example.mobile_project3.model.Nodes;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStreamReader;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@RestController
public class BusController {

    // get routeList passing through each nodes      param: 도시코드 정류소명    return: Map<버스번호 : 버스ID>
    @GetMapping("getRouteThrghNodeNm")
    public Map<String, String> getRouteThrghNodeNm(@RequestParam String citycode, String nodenm) throws IOException, JSONException {

        System.out.println("\n<getRouteThrghNodeNm>");
        Nodes nodes = getNodeID(citycode, nodenm);
        System.out.println("\nnodes = " + nodes.toString());
        Map<String, NodeInfo> node = nodes.getNodes();
        NodeInfo nodeInfo = node.get(nodenm);
        String nodeID = nodeInfo.getNodeid();
        System.out.println("\nnodenm: " + nodenm + " nodeID: " + nodeID);

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusSttnInfoInqireService/getSttnThrghRouteList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=DVNwYfz2bCeyEZsFkEwESH6ZvoZpDUhj%2FJ5em%2FruYkH7Fe%2FSmf48qyGZRbH2uKDbCP8M1NoY8SnBQzPJlQfdbA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode(citycode, "UTF-8")); /*도시코드*/
        urlBuilder.append("&" + URLEncoder.encode("nodeid","UTF-8") + "=" + URLEncoder.encode(nodeID, "UTF-8")); /*정류소ID*/
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
        JSONObject jObjects = new JSONObject(sb.toString());
        JSONObject items = jObjects.getJSONObject("response").getJSONObject("body");
        Map<String, String> buses = new HashMap<>();

        if(items.getInt("totalCount") > 1)
        {
            JSONArray item = items.getJSONObject("items").getJSONArray("item");
            for(int i=0; i< item.length(); i++)
            {
                JSONObject obj = item.getJSONObject(i);
                buses.put(obj.getString("routeno"), obj.getString("routeid"));
            }
        } else {
            JSONObject obj = items.getJSONObject("items").getJSONObject("item");
            buses.put(obj.getString("routeno"), obj.getString("routeid"));
        }
        System.out.println(buses);
        System.out.println("</getRouteThrghNodeNm>");
        return buses;
    }

    // get nodeID       param: 도시코드, 정류소명       return: Map< 정류소이름 : {위도, 경도, 정류소ID} >
    @GetMapping("/getNodeID")
    public Nodes getNodeID(@RequestParam String citycode, String nodenm) throws IOException, JSONException {
        System.out.println("\n<getNodeID>");
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusSttnInfoInqireService/getSttnNoList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=DVNwYfz2bCeyEZsFkEwESH6ZvoZpDUhj%2FJ5em%2FruYkH7Fe%2FSmf48qyGZRbH2uKDbCP8M1NoY8SnBQzPJlQfdbA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode(citycode, "UTF-8")); /*도시코드*/
        urlBuilder.append("&" + URLEncoder.encode("nodeNm","UTF-8") + "=" + URLEncoder.encode(nodenm, "UTF-8")); /*정류소명*/

        HttpURLConnection conn = httpURLConnection(urlBuilder.toString());

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
        //System.out.println(sb.toString());
        JSONObject jObjects = new JSONObject(sb.toString());
        JSONObject items = jObjects.getJSONObject("response").getJSONObject("body");
        Map<String, NodeInfo> node = new HashMap<>();
        NodeInfo nodeInfo;

        if(items.getInt("totalCount") > 1)
        {
            JSONArray item = items.getJSONObject("items").getJSONArray("item");
            for(int i=0; i< item.length(); i++)
            {
                JSONObject obj = item.getJSONObject(i);
                nodeInfo = new NodeInfo(obj.getString("gpslati"), obj.getString("gpslong"), obj.getString("nodeid"));
                node.put(obj.getString("nodenm"), nodeInfo);
            }
        } else {
            JSONObject obj = items.getJSONObject("items").getJSONObject("item");
            nodeInfo = new NodeInfo(obj.getString("gpslati"), obj.getString("gpslong"), obj.getString("nodeid"));
            node.put(obj.getString("nodenm"), nodeInfo);
        }
        Nodes nodes = new Nodes(node);

        System.out.println(nodes);
        System.out.println("</getNodeID>");
        return nodes;
    }

    // get busID        param: 도시코드, 노선번호       return: 노선ID
    @GetMapping("/getrouteID")
    public routeID getRouteID(@RequestParam String citycode, String routeno) throws IOException, JSONException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusRouteInfoInqireService/getRouteNoList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=DVNwYfz2bCeyEZsFkEwESH6ZvoZpDUhj%2FJ5em%2FruYkH7Fe%2FSmf48qyGZRbH2uKDbCP8M1NoY8SnBQzPJlQfdbA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode(citycode, "UTF-8")); /*도시코드 [상세기능4. 도시코드 목록 조회]에서 조회 가능*/
        urlBuilder.append("&" + URLEncoder.encode("routeNo","UTF-8") + "=" + URLEncoder.encode(routeno, "UTF-8")); /*노선번호*/

        HttpURLConnection conn = httpURLConnection(urlBuilder.toString());

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
        JSONObject jObjects = new JSONObject(sb.toString());
        JSONObject items = jObjects.getJSONObject("response").getJSONObject("body");
        List<String> routeid = new ArrayList<>();
        if(items.getInt("totalCount") > 1)
        {
            JSONArray item = items.getJSONObject("items").getJSONArray("item");
            for(int i=0; i< item.length(); i++)
            {
                JSONObject obj = item.getJSONObject(i);
                routeid.add(obj.getString("routeid"));
            }
        } else {
            routeid.add(items.getJSONObject("items").getJSONObject("item").getString("routeid"));
        }

        routeID routeID = new routeID(routeid);

        System.out.println(routeID);

        return routeID;
    }
    // get citycode
    @GetMapping("/getCityCode")
    public StringBuilder getCityCode() throws IOException, JSONException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusLcInfoInqireService/getCtyCodeList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Gva8D7gUzmoTHh8HdWjojVwEHL9r9WbBSJob74JTiIb5qUlt04y5lz%2FC4rDBV5dsazMMUxl79%2FHKf2M2Bybffg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/

        HttpURLConnection conn = httpURLConnection(urlBuilder.toString());

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

        JSONObject jObjects = new JSONObject(sb.toString());
        JSONObject items = jObjects.getJSONObject("response").getJSONObject("body");
        JSONArray item = items.getJSONObject("items").getJSONArray("item");

        System.out.println("-----get item-----");
        for(int i=0; i<item.length(); i++)
        {
            JSONObject obj = item.getJSONObject(i);
            String citycode = obj.getString("citycode");
            String cityname = obj.getString("cityname");
            System.out.println("<citycode: " + citycode + ", cityname: " + cityname + ">");
        }

        return sb;
    }

    public HttpURLConnection httpURLConnection(String geturl) throws IOException {
        URL url = new URL(geturl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        return conn;
    }
}