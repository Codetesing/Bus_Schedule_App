package com.example.demo.model;

import java.util.List;

public class Nodes {
    List<NodeInfo> up;    // 상행
    List<NodeInfo> down;  // 하행

    public Nodes(List<NodeInfo> up, List<NodeInfo> down) {
        this.up = up;
        this.down = down;
    }

    public List<NodeInfo> getUp() {
        return up;
    }

    public void setUp(List<NodeInfo> up) {
        this.up = up;
    }

    public List<NodeInfo> getDown() {
        return down;
    }

    public void setDown(List<NodeInfo> down) {
        this.down = down;
    }

    @Override
    public String toString() {
        return "Nodes{\n\t" +
                "up=" + up +
                ",\n\tdown=" + down +
                '}';
    }
}
