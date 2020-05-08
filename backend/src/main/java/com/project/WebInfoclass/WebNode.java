package com.project.WebInfoclass;

import java.util.List;

public class WebNode {
    private int id;
    private String MAC;
    private int state;
    private Double longitude;
    private Double latitude;

    public WebNode(int id, String MAC, int state, Double longitude, Double latitude){
        this.id = id;
        this.MAC = MAC;
        this.state = state;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public String getMAC() {
        return MAC;
    }

    public int getState() {
        return state;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
