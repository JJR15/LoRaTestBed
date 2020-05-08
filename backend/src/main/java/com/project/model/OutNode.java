package com.project.model;
import java.util.Date;

public class OutNode{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDev_eui() {
        return dev_eui;
    }

    public void setDev_eui(String dev_eui) {
        this.dev_eui = dev_eui;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getSpreading_factor() {
        return spreading_factor;
    }

    public void setSpreading_factor(int spreading_factor) {
        this.spreading_factor = spreading_factor;
    }

    public int getChannel_group() {
        return channel_group;
    }

    public void setChannel_group(int channel_group) {
        this.channel_group = channel_group;
    }

    public Date getLast_packet_time() {
        return last_packet_time;
    }

    public void setLast_packet_time(Date last_packet_time) {
        this.last_packet_time = last_packet_time;
    }

    private int id;
    private String name;
    private String dev_eui;
    private double longitude;
    private double latitude;
    private int spreading_factor;
    private int channel_group;
    private Date last_packet_time;
}