package com.example.mobile_project3.controller;

import com.example.mobile_project3.model.*;
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

    // 버스번호로 모든 경유 정류소와 운행중인 모든 노선의 위치(가까운 정류소) 출력
    // param: 도시코드 버스번호
    @GetMapping("/searchByRoute")
    public void searchByRoute(@RequestParam String citycode, String routenm) throws JSONException, IOException {
        System.out.println("\n<getAllNodes>");

        String routeid = getRouteID(citycode, routenm);
        System.out.println("routeID: " + routeid);
        Nodes nodes = getAllNodes(citycode, routeid);
        Routes routes = getAllRoutes(citycode, routeid);

        System.out.println(nodes.toString());
        System.out.println(routes.toString());

        int j = 0;
        for(int i=1; i < nodes.getUp().size(); i++)
        {
            int time = nodes.getUp().get(i-1).getDuration();
            // 버스의 다음 정류소가 i일 때
            if(Objects.equals(routes.getRoutes().get(j).getNodeid(), nodes.getUp().get(i-1).getNodeid()))
            {
                System.out.println("nodenm: " + nodes.getUp().get(i).getNodenm() + " routenm: " + routes.getRoutes().get(j).getRoutenm());

                // 버스에서 정류소까지 걸리는 시간으로 초기화
                time = getRoutesDuration("22", nodes.getUp().get(i).getNodeid(), routes.getRoutes().get(j).getRouteid());
                j++;
            }
            else {
                // 해당 정류소에 버스가 없으면 이동시간 더하기
                time += NaviController.navigation(nodes.getUp().get(i-1).getGpslong() + "," + nodes.getUp().get(i-1).getGpslati(), nodes.getUp().get(i).getGpslong() + "," + nodes.getUp().get(i).getGpslati(), "trafast");
            }

            // 다음 정류소까지 걸리는 시간 설정
            nodes.getUp().get(i).setDuration(time);
            System.out.println("time: " + time);
        }
        System.out.println("\n</getAllNodes>");
    }
    // 노선이 경유하는 모든 정류소 정보
    // param: 도시코드 버스번호  return List<NodeInfo> 상행(정류소이름, 정류소ID, 위도, 경도), 하행(정류소이름, 정류소ID, 위도, 경도)
    @GetMapping("/getAllNodes")
    public Nodes getAllNodes(@RequestParam String citycode, String routeid) throws IOException, JSONException {
        System.out.println("\n<getAllNodes>");

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
        System.out.println(sb.toString());
        JSONObject jObjects = new JSONObject(sb.toString());
        JSONArray items = jObjects.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");

        NodeInfo nodeInfo;
        List<NodeInfo> up = new ArrayList<>();
        List<NodeInfo> down = new ArrayList<>();

        for(int i=0; i< items.length(); i++)
        {
            JSONObject obj = items.getJSONObject(i);
            nodeInfo = new NodeInfo(obj.getString("nodenm"), obj.getString("nodeid"), obj.getString("gpslati"), obj.getString("gpslong"));
            int code = obj.getInt("updowncd");
            // 상행
            if(code == 0)
            {
                up.add(nodeInfo);
            }// 하행
            else if(code == 1)
            {
                down.add(nodeInfo);
            }

        }
        System.out.println("</getAllNodes>");
        return new Nodes(up, down);
    }

    // 정류소별 특정노선버스 도착예정정보
    // param: 도시코드 정류소ID, 버스ID
    @GetMapping("/getRoutesDuration")
    public int getRoutesDuration(@RequestParam String citycode, String nodeid, String routeid) throws IOException, JSONException {
        System.out.println("\n<getRoutesDuration>");
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/ArvlInfoInqireService/getSttnAcctoSpcifyRouteBusArvlPrearngeInfoList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Gva8D7gUzmoTHh8HdWjojVwEHL9r9WbBSJob74JTiIb5qUlt04y5lz%2FC4rDBV5dsazMMUxl79%2FHKf2M2Bybffg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
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
        int time = 0;

        if(items.getInt("totalCount") > 1)
        {
            time = items.getJSONObject("items").getJSONArray("item").getJSONObject(0).getInt("arrtime");
        } else {
            time = items.getJSONObject("items").getJSONObject("item").getInt("arrtime");
        }


        System.out.println("버스에서 가장 가까운 정류소까지 시간: " + time * 1000);
        System.out.println("\n</getRoutesDuration>");
        return time;
    }

    // 노선별 버스 위치 목록 조회
    // param: 도시코드 버스번호  return 버스정보와 버스가 위치하는 정류소정보
    @GetMapping("/getAllRoutes")
    public Routes getAllRoutes(@RequestParam String citycode, String routeid) throws IOException, JSONException {
        System.out.println("\n<getAllRoutes>");

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusLcInfoInqireService/getRouteAcctoBusLcList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=Gva8D7gUzmoTHh8HdWjojVwEHL9r9WbBSJob74JTiIb5qUlt04y5lz%2FC4rDBV5dsazMMUxl79%2FHKf2M2Bybffg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
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
        System.out.println(sb.toString());
        JSONObject jObjects = new JSONObject(sb.toString());
        JSONObject items = jObjects.getJSONObject("response").getJSONObject("body");

        RouteInfo routeInfo;
        List<RouteInfo> routes = new ArrayList<>();

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

        System.out.println("\n</getAllRoutes>");
        return new Routes(routes);
    }

    // 정류소별 도착예정정보 목록 조회
    @GetMapping("/maybe")
    public String maybe(@RequestParam String citycode, String nodenm) throws IOException, JSONException {

        System.out.println("\n<maybe>");
        String nodeID = getNodeID(citycode, nodenm);

        System.out.println("\nnodenm: " + nodenm + " nodeID: " + nodeID);

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/ArvlInfoInqireService/getSttnAcctoArvlPrearngeInfoList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Gva8D7gUzmoTHh8HdWjojVwEHL9r9WbBSJob74JTiIb5qUlt04y5lz%2FC4rDBV5dsazMMUxl79%2FHKf2M2Bybffg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode(citycode, "UTF-8")); /*도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능*/
        urlBuilder.append("&" + URLEncoder.encode("nodeId","UTF-8") + "=" + URLEncoder.encode(nodeID, "UTF-8")); /*정류소ID [국토교통부(TAGO)_버스정류소정보]에서 조회가능*/

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


        System.out.println("\n</maybe>");
        return (sb.toString());
    }

    // get routeList passing through each nodes      param: 도시코드 정류소명    return: Map<버스번호 : 버스ID>
    @GetMapping("getRouteThrghNodeNm")
    public BusID getRouteThrghNodeNm(@RequestParam String citycode, String nodenm) throws IOException, JSONException {

        System.out.println("\n<getRouteThrghNodeNm>");
        String nodeID = getNodeID(citycode, nodenm);

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
        return new BusID(buses);
    }

    // get nodeID       param: 도시코드, 정류소명       return: Map< 정류소이름 : {위도, 경도, 정류소ID} >
    @GetMapping("/getNodeID")
    public String getNodeID(@RequestParam String citycode, String nodenm) throws IOException, JSONException {
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
        System.out.println(sb.toString());
        JSONObject jObjects = new JSONObject(sb.toString());
        JSONObject items = jObjects.getJSONObject("response").getJSONObject("body");
        String item;
        List<String> nodenms = new ArrayList<>();
        if(items.getInt("totalCount") > 1)
        {
            item = items.getJSONObject("items").getJSONArray("item").getJSONObject(0).getString("nodeid");

            // 비슷한 이름이 여러개일때 모두 출력...
            JSONArray Array = items.getJSONObject("items").getJSONArray("item");
            for(int i=0; i< Array.length(); i++)
            {
                JSONObject obj = Array.getJSONObject(i);
                //nodenms.add(obj.getString("nodenm"));
                System.out.println(obj.getString("nodenm"));
            }
        } else {
            item = items.getJSONObject("items").getJSONObject("item").getString("nodeid");
        }

        System.out.println("\n</getNodeID>");
        return item;
    }

    // get routeID        param: 도시코드, 노선번호       return: 노선ID
    @GetMapping("/getrouteID")
    public String getRouteID(@RequestParam String citycode, String routenm) throws IOException, JSONException {
        System.out.println("\n<getrouteID>");
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusRouteInfoInqireService/getRouteNoList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=DVNwYfz2bCeyEZsFkEwESH6ZvoZpDUhj%2FJ5em%2FruYkH7Fe%2FSmf48qyGZRbH2uKDbCP8M1NoY8SnBQzPJlQfdbA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
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


        String item;
        List<String> nodenms = new ArrayList<>();
        if(items.getInt("totalCount") > 1)
        {
            item = items.getJSONObject("items").getJSONArray("item").getJSONObject(0).getString("routeid");
            // 비슷한 이름이 여러개일때 모두 출력...
            JSONArray Array = items.getJSONObject("items").getJSONArray("item");
            for(int i=0; i< Array.length(); i++)
            {
                JSONObject obj = Array.getJSONObject(i);
                //nodenms.add(obj.getString("routeno"));
                System.out.println(obj.getString("routeno"));
            }
        } else {
            item = items.getJSONObject("items").getJSONObject("item").getString("routeid");
        }

        System.out.println("\n</getNodeID>");
        return item;
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
            System.out.println(citycode + " : " + cityname );
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
