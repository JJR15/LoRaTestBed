package com.project.WebInfoclass;

import java.util.Date;

public class WebUserOrder {
    private int id;
    private Date starttime;
    private Date endtime;

    public WebUserOrder(int id, Date starttime, Date endtime){
        this.id = id;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public int getId() {
        return id;
    }

    public Date getStarttime() {
        return starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}
