package com.example.mobile_project3.model;


import java.util.Arrays;
import java.util.List;

public class routeID {
    String[] routeID;

    public routeID(List<String> routeID) {
        this.routeID = routeID.toArray(new String[0]);
    }

    public String[] getRouteID() {
        return routeID;
    }

    public void setRouteID(String[] routeID) {
        this.routeID = routeID;
    }

    @Override
    public String toString() {
        return "BusID{" +
                "routeID=" + Arrays.toString(routeID) +
                '}';
    }
}
