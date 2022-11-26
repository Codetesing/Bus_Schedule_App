package com.example.mobile_project3.model;

import java.util.Map;

public class Buses {
    Map<String, String> bus;

    public Buses(Map<String, String> bus) {
        this.bus = bus;
    }

    public Map<String, String> getBus() {
        return bus;
    }

    public void setBus(Map<String, String> bus) {
        this.bus = bus;
    }

    @Override
    public String toString() {
        return "Buses{" +
                "bus=" + bus +
                '}';
    }
}
