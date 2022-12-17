package com.example.demo.model;

import java.util.List;

public class Routes {
    List<RouteInfo> routes;     // 노선들 정보

    public Routes(List<RouteInfo> routes) {
        this.routes = routes;
    }

    public List<RouteInfo> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteInfo> routes) {
        this.routes = routes;
    }

    @Override
    public String toString() {
        return "Routes{\n\t" +
                "routes=" + routes +
                '}';
    }
}
