package com.project.model;

import java.util.Date;

public class OutNode {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lora_web_node.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lora_web_node.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lora_web_node.dev_eui
     *
     * @mbggenerated
     */
    private String devEui;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lora_web_node.spreading_factor
     *
     * @mbggenerated
     */
    private Integer spreadingFactor;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lora_web_node.channel_group
     *
     * @mbggenerated
     */
    private Integer channelGroup;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lora_web_node.longitude
     *
     * @mbggenerated
     */
    private Double longitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lora_web_node.latitude
     *
     * @mbggenerated
     */
    private Double latitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column lora_web_node.last_packet_time
     *
     * @mbggenerated
     */
    private Date lastPacketTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lora_web_node
     *
     * @mbggenerated
     */
    public OutNode(Integer id, String name, String devEui, Integer spreadingFactor, Integer channelGroup, Double longitude, Double latitude, Date lastPacketTime) {
        this.id = id;
        this.name = name;
        this.devEui = devEui;
        this.spreadingFactor = spreadingFactor;
        this.channelGroup = channelGroup;
        this.longitude = longitude;
        this.latitude = latitude;
        this.lastPacketTime = lastPacketTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lora_web_node
     *
     * @mbggenerated
     */
    public OutNode() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lora_web_node.id
     *
     * @return the value of lora_web_node.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lora_web_node.id
     *
     * @param id the value for lora_web_node.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lora_web_node.name
     *
     * @return the value of lora_web_node.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lora_web_node.name
     *
     * @param name the value for lora_web_node.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lora_web_node.dev_eui
     *
     * @return the value of lora_web_node.dev_eui
     *
     * @mbggenerated
     */
    public String getDevEui() {
        return devEui;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lora_web_node.dev_eui
     *
     * @param devEui the value for lora_web_node.dev_eui
     *
     * @mbggenerated
     */
    public void setDevEui(String devEui) {
        this.devEui = devEui == null ? null : devEui.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lora_web_node.spreading_factor
     *
     * @return the value of lora_web_node.spreading_factor
     *
     * @mbggenerated
     */
    public Integer getSpreadingFactor() {
        return spreadingFactor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lora_web_node.spreading_factor
     *
     * @param spreadingFactor the value for lora_web_node.spreading_factor
     *
     * @mbggenerated
     */
    public void setSpreadingFactor(Integer spreadingFactor) {
        this.spreadingFactor = spreadingFactor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lora_web_node.channel_group
     *
     * @return the value of lora_web_node.channel_group
     *
     * @mbggenerated
     */
    public Integer getChannelGroup() {
        return channelGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lora_web_node.channel_group
     *
     * @param channelGroup the value for lora_web_node.channel_group
     *
     * @mbggenerated
     */
    public void setChannelGroup(Integer channelGroup) {
        this.channelGroup = channelGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lora_web_node.longitude
     *
     * @return the value of lora_web_node.longitude
     *
     * @mbggenerated
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lora_web_node.longitude
     *
     * @param longitude the value for lora_web_node.longitude
     *
     * @mbggenerated
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lora_web_node.latitude
     *
     * @return the value of lora_web_node.latitude
     *
     * @mbggenerated
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lora_web_node.latitude
     *
     * @param latitude the value for lora_web_node.latitude
     *
     * @mbggenerated
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column lora_web_node.last_packet_time
     *
     * @return the value of lora_web_node.last_packet_time
     *
     * @mbggenerated
     */
    public Date getLastPacketTime() {
        return lastPacketTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column lora_web_node.last_packet_time
     *
     * @param lastPacketTime the value for lora_web_node.last_packet_time
     *
     * @mbggenerated
     */
    public void setLastPacketTime(Date lastPacketTime) {
        this.lastPacketTime = lastPacketTime;
    }
}