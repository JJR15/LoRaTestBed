package com.project.WebInfoclass;

import java.util.Date;

public class WebNodeOrder {
    private String username;
    private Date starttime;
    private Date endtime;

    public WebNodeOrder(String username, Date starttime, Date endtime){
        this.username = username;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public String getUsername() { return username; }

    public Date getStarttime() {
        return starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setUsername(String username) { this.username = username; }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}
