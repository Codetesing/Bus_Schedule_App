package com.example.demo.controller;

import com.example.demo.model.IDList;
import com.example.demo.model.RouteInfo;
import com.example.demo.model.Routes;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class NodeController {
    // 1
    // 정류소이름을 입력하면 해당하는 정류소 id 반환
    // 비슷한 이름의 정류소에 대해서 모두 반환
    // param: citycode, 정류소이름     return: IDList(정류소이름, 정류소id) or null(해당하는 번호가 없음)
    @GetMapping("/getNodeID")
    public IDList getNodeID(@RequestParam String citycode, String nodenm) throws IOException, JSONException {
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
        // System.out.println(sb.toString());
        JSONObject jObjects = new JSONObject(sb.toString());
        JSONObject items = jObjects.getJSONObject("response").getJSONObject("body");

        List<String> nodenms = new ArrayList<>();
        List<String> nodeids = new ArrayList<>();

        // 일치하는 값이 없음(null)
        if (items.getInt("totalCount") == 0)
        {
            return null;
        }
        if(items.getInt("totalCount") > 1)
        {
            // 비슷한 이름이 여러개일때 모두 출력...
            JSONArray Array = items.getJSONObject("items").getJSONArray("item");
            for(int i=0; i< Array.length(); i++)
            {
                JSONObject obj = Array.getJSONObject(i);
                nodenms.add(obj.getString("nodenm"));
                nodeids.add(obj.getString("nodeid"));
            }
        } else {
            nodenms.add(items.getJSONObject("items").getJSONObject("item").getString("nodenm"));
            nodeids.add(items.getJSONObject("items").getJSONObject("item").getString("nodeid"));
        }
        return new IDList(nodenms, nodeids);
    }

    // 2
    // 정류소를 경유하는 노선 group by 노선
    // param: citycode, 정류소이름    return: IDList(정류소이름, 정류소id) or null(해당하는 번호가 없음)
    @GetMapping("getRouteThrghNodeNm")
    public IDList getRouteThrghNodeNm(@RequestParam String citycode, String nodeid) throws IOException, JSONException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusSttnInfoInqireService/getSttnThrghRouteList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=DVNwYfz2bCeyEZsFkEwESH6ZvoZpDUhj%2FJ5em%2FruYkH7Fe%2FSmf48qyGZRbH2uKDbCP8M1NoY8SnBQzPJlQfdbA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode(citycode, "UTF-8")); /*도시코드*/
        urlBuilder.append("&" + URLEncoder.encode("nodeid","UTF-8") + "=" + URLEncoder.encode(nodeid, "UTF-8")); /*정류소ID*/
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

        List<String> nodenms = new ArrayList<>();
        List<String> nodeids = new ArrayList<>();

        // 일치하는 값이 없음(null)
        if (items.getInt("totalCount") == 0)
        {
            return null;
        }
        if(items.getInt("totalCount") > 1)
        {
            JSONArray Array = items.getJSONObject("items").getJSONArray("item");
            for(int i=0; i< Array.length(); i++)
            {
                JSONObject obj = Array.getJSONObject(i);
                nodenms.add(obj.getString("nodenm"));
                nodeids.add(obj.getString("nodeid"));
            }
        } else {
            nodenms.add(items.getJSONObject("items").getJSONObject("item").getString("nodenm"));
            nodeids.add(items.getJSONObject("items").getJSONObject("item").getString("nodeid"));
        }

        return new IDList(nodenms, nodeids);
    }

    // 3
    // 정류소 도착예정인 모든 노선 정보 목록 조회
    // param: citycode, 정류소id           return: Route(노선번호, 노선id, 정류소이름, 정류소id, null, null, 도착예정시간) or null
    @GetMapping("/maybe")
    public Routes maybe(@RequestParam String citycode, String nodeid) throws IOException, JSONException {

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/ArvlInfoInqireService/getSttnAcctoArvlPrearngeInfoList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Gva8D7gUzmoTHh8HdWjojVwEHL9r9WbBSJob74JTiIb5qUlt04y5lz%2FC4rDBV5dsazMMUxl79%2FHKf2M2Bybffg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode(citycode, "UTF-8")); /*도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능*/
        urlBuilder.append("&" + URLEncoder.encode("nodeId","UTF-8") + "=" + URLEncoder.encode(nodeid, "UTF-8")); /*정류소ID [국토교통부(TAGO)_버스정류소정보]에서 조회가능*/

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

        RouteInfo routeInfo;
        List<RouteInfo> routes = new ArrayList<>();

        // 일치하는 값이 없음(null)
        if (items.getInt("totalCount") == 0)
        {
            return null;
        }
        if(items.getInt("totalCount") > 1)
        {
            JSONArray item = items.getJSONObject("items").getJSONArray("item");
            for(int i=0; i< item.length(); i++)
            {
                JSONObject obj = item.getJSONObject(i);
                routeInfo = new RouteInfo(obj.getString("routeno"), obj.getString("routeid"), obj.getString("nodeid"), obj.getString("nodenm"), null, null, obj.getInt("arrtime"));
                routes.add(routeInfo);
            }
        } else {
            JSONObject obj = items.getJSONObject("items").getJSONObject("item");
            routeInfo = new RouteInfo(obj.getString("routeno"), obj.getString("routeid"), obj.getString("nodeid"), obj.getString("nodenm"), null, null, obj.getInt("arrtime"));
            routes.add(routeInfo);
        }

        return new Routes(routes);
    }

    public HttpURLConnection httpURLConnection(String geturl) throws IOException {
        URL url = new URL(geturl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        //System.out.println("Response code: " + conn.getResponseCode());
        return conn;
    }
}
