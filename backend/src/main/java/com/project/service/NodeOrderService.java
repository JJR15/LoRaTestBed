package com.project.service;

import com.project.model.Gatewayinfo;
import com.project.model.Nodeinfo;
import com.project.model.Timeorder;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface NodeOrderService {

    String getUser(int node_id);

    Timeorder getLastestOrderTime(); //获取最新一行的预订信息

    boolean canOrderNode(Date startTime, Date endTime);

    List<Timeorder> getOrderedNodeTime(); //获取使用中的预定信息

    List<Nodeinfo> getAllNodes(); //获取所有节点信息

    List<Gatewayinfo> getAllGateways();//

    boolean orderNode(String username, Date startTime, Date endTime);

    List<Integer> updateOrderStatus(String username, List<Integer> orderIds); //更新订单编号的状态通过审核

    List<Object> canAccessNodeAtTime(String username);

    List<Timeorder> getUserOrderInfos(String username); // 某个用户所有可用的预约，不包括进行中的

    List<Timeorder> getUserAllOrders(String username); // 某个用户所有预约信息

    List<Integer> cancelOrderNode(String username, List<Integer> orderIds); //取消预约，返回null，删除成功，否则返回删除失败的item

    Timeorder getNowOrder(); //获取当前的预约信息，可能是 null

    List<Timeorder> getTenDaysOrder(); // 获取未来10天的预约信息，可能是null

    //下面的函数假设对应id的node存在

//    List<Timeorder> getOrderedNodeTime(int node_id);

    ////////////////////////////////////////////////////////////
    boolean stopUseNode(int node_id, String username);

    //系统定时任务
    //void updateNodesAccess();

    //void checkNodeAccessOutDate();

    // void checkNodeAccessNewOwner(int node_id);

    //获取节点位置
    List<Nodeinfo> getNodeInfo();
}
