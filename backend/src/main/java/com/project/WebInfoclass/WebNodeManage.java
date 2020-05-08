package com.project.WebInfoclass;

import java.util.Date;

public class WebNodeManage {
    private WebNode nodeInfo;
    private Date starttime;
    private Date endtime;

    public WebNodeManage(WebNode nodeInfo, Date starttime, Date endtime){
        this.nodeInfo = nodeInfo;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public void setNodeInfo(WebNode nodeInfo) { this.nodeInfo = nodeInfo; }

    public void setStarttime(Date starttime) { this.starttime = starttime; }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public WebNode getNodeInfo() { return nodeInfo; }

    public Date getStarttime() { return starttime; }

    public Date getEndtime() {
        return endtime;
    }
}
