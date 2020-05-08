package com.project.model;

public class Gatewayinfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gatewayinfo.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gatewayinfo.PI_MAC
     *
     * @mbggenerated
     */
    private String piMac;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gatewayinfo.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gatewayinfo.username
     *
     * @mbggenerated
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gatewayinfo.longitude
     *
     * @mbggenerated
     */
    private Double longitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gatewayinfo.latitude
     *
     * @mbggenerated
     */
    private Double latitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gatewayinfo.ip
     *
     * @mbggenerated
     */
    private String ip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gatewayinfo.frequency
     *
     * @mbggenerated
     */
    private Integer frequency;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gatewayinfo
     *
     * @mbggenerated
     */
    public Gatewayinfo(Integer id, String piMac, Integer status, String username, Double longitude, Double latitude, String ip, Integer frequency) {
        this.id = id;
        this.piMac = piMac;
        this.status = status;
        this.username = username;
        this.longitude = longitude;
        this.latitude = latitude;
        this.ip = ip;
        this.frequency = frequency;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gatewayinfo
     *
     * @mbggenerated
     */
    public Gatewayinfo() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gatewayinfo.id
     *
     * @return the value of gatewayinfo.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gatewayinfo.id
     *
     * @param id the value for gatewayinfo.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gatewayinfo.PI_MAC
     *
     * @return the value of gatewayinfo.PI_MAC
     *
     * @mbggenerated
     */
    public String getPiMac() {
        return piMac;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gatewayinfo.PI_MAC
     *
     * @param piMac the value for gatewayinfo.PI_MAC
     *
     * @mbggenerated
     */
    public void setPiMac(String piMac) {
        this.piMac = piMac == null ? null : piMac.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gatewayinfo.status
     *
     * @return the value of gatewayinfo.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gatewayinfo.status
     *
     * @param status the value for gatewayinfo.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gatewayinfo.username
     *
     * @return the value of gatewayinfo.username
     *
     * @mbggenerated
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gatewayinfo.username
     *
     * @param username the value for gatewayinfo.username
     *
     * @mbggenerated
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gatewayinfo.longitude
     *
     * @return the value of gatewayinfo.longitude
     *
     * @mbggenerated
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gatewayinfo.longitude
     *
     * @param longitude the value for gatewayinfo.longitude
     *
     * @mbggenerated
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gatewayinfo.latitude
     *
     * @return the value of gatewayinfo.latitude
     *
     * @mbggenerated
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gatewayinfo.latitude
     *
     * @param latitude the value for gatewayinfo.latitude
     *
     * @mbggenerated
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gatewayinfo.ip
     *
     * @return the value of gatewayinfo.ip
     *
     * @mbggenerated
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gatewayinfo.ip
     *
     * @param ip the value for gatewayinfo.ip
     *
     * @mbggenerated
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gatewayinfo.frequency
     *
     * @return the value of gatewayinfo.frequency
     *
     * @mbggenerated
     */
    public Integer getFrequency() {
        return frequency;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gatewayinfo.frequency
     *
     * @param frequency the value for gatewayinfo.frequency
     *
     * @mbggenerated
     */
    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }
}