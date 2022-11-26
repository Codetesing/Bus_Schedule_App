package com.example.mobile_project3.model;

import java.util.Map;

public class Nodes {
    Map<String, NodeInfo> node;

    public Nodes(Map<String, NodeInfo> nodes) {
        this.node = nodes;
    }

    public Map<String, NodeInfo> getNodes() {
        return node;
    }

    public void setNodes(Map<String, NodeInfo> nodes) {
        this.node = nodes;
    }

    @Override
    public String toString() {
        return "NodeList{" +
                "nodes=" + node +
                '}';
    }
}
