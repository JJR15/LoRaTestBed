package com.project.service;

import com.project.WebInfoclass.WebNode;
import com.project.WebInfoclass.WebNodeManage;
import com.project.WebInfoclass.WebNodeOrder;
import com.project.WebInfoclass.WebUserOrder;
import com.project.model.Nodeinfo;
import com.project.model.Timeorder;

import java.util.List;

public interface SystemInfoService {

    boolean checkNodeExist(int node_id);

    Nodeinfo getNodeInfo(int node_id);

    List<Nodeinfo> getAllNodes();

    // 用户总数
    int getTotal_user();

    // 节点总数
    int getTotal_node();

    // 运行节点总数
    int getRun_node();

    WebNode toWebNode(Nodeinfo node);

    WebNodeManage toWebNodeManage(Timeorder order);

    WebUserOrder toWebUserOrder(Timeorder order);

    WebNodeOrder toWebNodeOrder(Timeorder node);
}
