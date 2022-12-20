package com.example.demo.controller;

import com.example.demo.model.*;
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
import java.util.List;

@RestController
public class RouteController {
    // 1
    // 노선번호를 입력하면 해당하는 노선의 id 반환
    // 비슷한 번호에 대해서 모두 반환
    // param: citycode, 노선번호     return: IDList(노선번호, 노선id) or null(해당하는 번호가 없음)
    @GetMapping("/getrouteID")
    public String getRouteID(@RequestParam String citycode, String routenm) throws IOException, JSONException {
        System.out.println("getrouteID");
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusRouteInfoInqireService/getRouteNoList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=DVNwYfz2bCeyEZsFkEwESH6ZvoZpDUhj%2FJ5em%2FruYkH7Fe%2FSmf48qyGZRbH2uKDbCP8M1NoY8SnBQzPJlQfdbA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode(citycode, "UTF-8")); /*도시코드 [상세기능4. 도시코드 목록 조회]에서 조회 가능*/
        urlBuilder.append("&" + URLEncoder.encode("routeNo","UTF-8") + "=" + URLEncoder.encode(routenm, "UTF-8")); /*노선번호*/

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

        List<String> routenms = new ArrayList<>();
        List<String> routeids = new ArrayList<>();

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
                routenms.add(obj.getString("routeno"));
                routeids.add(obj.getString("routeid"));
            }
        } else {
            routenms.add(items.getJSONObject("items").getJSONObject("item").getString("routeno"));
            routeids.add(items.getJSONObject("items").getJSONObject("item").getString("routeid"));
        }
        return routeids.get(0);
    }

    // 2
    // 노선이 경유하는 모든 정류소 정보
    // param: 도시코드 버스번호  return List<NodeInfo> 상행(정류소이름, 정류소ID, 위도, 경도), 하행(정류소이름, 정류소ID, 위도, 경도)
    @GetMapping("/getAllNodes")
    public Nodes getAllNodes(@RequestParam String citycode, String routeid) throws IOException, JSONException {
        System.out.println("getAllNodes");
        System.out.println(routeid);
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusRouteInfoInqireService/getRouteAcctoThrghSttnList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=DVNwYfz2bCeyEZsFkEwESH6ZvoZpDUhj%2FJ5em%2FruYkH7Fe%2FSmf48qyGZRbH2uKDbCP8M1NoY8SnBQzPJlQfdbA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode(citycode, "UTF-8")); /*도시코드 [상세기능4. 도시코드 목록 조회]에서 조회 가능*/
        urlBuilder.append("&" + URLEncoder.encode("routeId","UTF-8") + "=" + URLEncoder.encode(routeid, "UTF-8")); /*노선ID [상세기능1. 노선번호목록 조회]에서 조회 가능*/
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
        JSONArray items = jObjects.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");

        NodeInfo nodeInfo;
        List<NodeInfo> up = new ArrayList<>();
        List<NodeInfo> down = new ArrayList<>();

        Routes r = getAllRoutes(citycode, routeid);

        int cnt = 0;
        for(int i=0; i< items.length(); i++)
        {
            JSONObject obj = items.getJSONObject(i);
            nodeInfo = new NodeInfo(obj.getString("nodenm"), obj.getString("nodeid"), obj.getString("gpslati"), obj.getString("gpslong"));
            int code = obj.getInt("updowncd");

            for(RouteInfo tr : r.getRoutes()) {
                if (nodeInfo.getNodeid().equals(tr.getNodeid())) {
                    nodeInfo.setbus(true);
                    cnt++;
                }
            }
            // 상행
            if(code == 0)
            {
                up.add(nodeInfo);
            }

            // 하행
            else if(code == 1)
            {
                down.add(nodeInfo);
            }
        }
        System.out.println(cnt);

        return new Nodes(up, down);
    }

    // 3
    // 노선별 버스 위치 목록 조회
    // param: 도시코드 버스번호  return 버스정보와 버스가 위치하는 정류소정보(없으면 null)
    @GetMapping("/getAllRoutes")
    public Routes getAllRoutes(@RequestParam String citycode, String routeid) throws IOException, JSONException {
        System.out.println("getAllRoutes");
        System.out.println(routeid);
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusLcInfoInqireService/getRouteAcctoBusLcList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=Gva8D7gUzmoTHh8HdWjojVwEHL9r9WbBSJob74JTiIb5qUlt04y5lz%2FC4rDBV5dsazMMUxl79%2FHKf2M2Bybffg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode(citycode, "UTF-8")); /*도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능*/
        urlBuilder.append("&" + URLEncoder.encode("routeId","UTF-8") + "=" + URLEncoder.encode(routeid, "UTF-8")); /*노선ID [국토교통부(TAGO)_버스노선정보]에서 조회가능*/
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
                routeInfo = new RouteInfo(obj.getString("routenm"), routeid, obj.getString("nodeid"), obj.getString("nodenm"), obj.getString("gpslati"), obj.getString("gpslong"));
                routes.add(routeInfo);
            }
        } else {
            JSONObject obj = items.getJSONObject("items").getJSONObject("item");
            routeInfo = new RouteInfo(obj.getString("routenm"), routeid, obj.getString("nodeid"), obj.getString("nodenm"), obj.getString("gpslati"), obj.getString("gpslong"));
            routes.add(routeInfo);
        }

        return new Routes(routes);
    }

    // 4
    // 정류소별 특정노선버스 도착예정정보
    // param: 도시코드 정류소ID, 버스ID              return: 1/1000초 or -1(값이 없을 때)
    @GetMapping("/getRoutesDuration")
    public JSONObject getRoutesDuration(@RequestParam String citycode, String nodeid, String routeid) throws IOException, JSONException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/ArvlInfoInqireService/getSttnAcctoSpcifyRouteBusArvlPrearngeInfoList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Gva8D7gUzmoTHh8HdWjojVwEHL9r9WbBSJob74JTiIb5qUlt04y5lz%2FC4rDBV5dsazMMUxl79%2FHKf2M2Bybffg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode(citycode, "UTF-8")); /*도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능*/
        urlBuilder.append("&" + URLEncoder.encode("nodeId","UTF-8") + "=" + URLEncoder.encode(nodeid, "UTF-8")); /*정류소ID [국토교통부(TAGO)_버스정류소정보]에서 조회가능*/
        urlBuilder.append("&" + URLEncoder.encode("routeId","UTF-8") + "=" + URLEncoder.encode(routeid, "UTF-8")); /*노선ID*/
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
        JSONObject bus = null;

        // 일치하는 값이 없음(-1)
        if (items.getInt("totalCount") == 0)
        {
            return null;
        }
        if(items.getInt("totalCount") > 1)
        {
            bus = items.getJSONObject("items").getJSONArray("item").getJSONObject(0);
            for(int i = 1; i < items.getInt("totalCount"); i++)
            {
                if(bus.getInt("arrprevstationcnt") > items.getJSONObject("items").getJSONArray("item").getJSONObject(i).getInt("arrprevstationcnt"))
                    bus = items.getJSONObject("items").getJSONArray("item").getJSONObject(i);
            }
        } else {
            bus = items.getJSONObject("items").getJSONObject("item");
        }

        return bus;
    }

    // 정류소에 도착하는 버스 네비게이션으로 계산
    // param: citycode, nodeid, routeid     return: 도착예정시간(1/1000초)
    @GetMapping("/duration")
    public int duration(@RequestParam String citycode, String nodeid, String routeid) throws JSONException, IOException {
        List<NodeInfo> List = getAllNodes(citycode, routeid).getDown();
        int index = List.indexOf(List.stream().filter(x->x.getNodeid().equals(nodeid)).findAny().get());
        //System.out.println(index);
        JSONObject dst = getRoutesDuration(citycode, nodeid, routeid);
        int time = 0;
        // 도착예정버스가 없음
        if(dst == null)
            return -1;
        while(dst.getInt("arrprevstationcnt") > 1)
        {
            time += NaviController.navigation(List.get(index-1).getGpslong()+","+ List.get(index-1).getGpslati(), List.get(index).getGpslong()+","+ List.get(index).getGpslati(), "trafast");

            index--;
            dst = getRoutesDuration(citycode, List.get(index).getNodeid(), routeid);
        }
        time +=  getRoutesDuration(citycode, dst.getString("nodeid"), routeid).getInt("arrtime") * 1000;
        return time;
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
