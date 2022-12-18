package com.example.demo.model;

import java.util.List;

public class IDList {
    private List<String> nms;
    private List<String> ids;

    public IDList( List<String> nms, List<String> ids) {
        this.nms = nms;
        this.ids = ids;

    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<String> getNms() {
        return nms;
    }

    public void setNms(List<String> nms) {
        this.nms = nms;
    }

    @Override
    public String toString() {
        return "IDList{" +
                "ids=" + ids +
                ", nms=" + nms +
                '}';
    }
}
