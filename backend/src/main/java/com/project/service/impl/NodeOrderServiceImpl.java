package com.project.service.impl;

import com.project.mapper.GatewayinfoMapper;
import com.project.mapper.NodeinfoMapper;
import com.project.mapper.TimeorderMapper;
import com.project.model.*;
import com.project.service.JuraService;
import com.project.service.NodeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.project.util.CONSTANT.*;

@Service("nodeOrderServiceImpl")
public class NodeOrderServiceImpl implements NodeOrderService {

    @Autowired
    private TimeorderMapper timeorderMapper;
    @Autowired
    private NodeinfoMapper nodeinfoMapper;
    @Autowired
    private GatewayinfoMapper gatewayinfoMapper;

    @Autowired
    private JuraService juraService;

    @Override
    public String getUser(int node_id){
        NodeinfoExample e = new NodeinfoExample();
        NodeinfoExample.Criteria c = e.createCriteria();
        c.andIdEqualTo(node_id);
        List<Nodeinfo> nodes = nodeinfoMapper.selectByExample(e);
        if(nodes.size() != 1) return null;
        return nodes.get(0).getUsername();
    }

    @Override
    public List<Nodeinfo> getAllNodes() {
        NodeinfoExample e = new NodeinfoExample();
        NodeinfoExample.Criteria c = e.createCriteria();
        c.andIdIsNotNull();
        return nodeinfoMapper.selectByExample(e);
    }

    @Override
    public List<Timeorder> getOrderedNodeTime(){
        Date now = new Date();
        TimeorderExample e = new TimeorderExample();
        TimeorderExample.Criteria c = e.createCriteria();
        c.andEndtimeGreaterThan(now);
        return timeorderMapper.selectByExample(e);
    }

    @Override
    public Timeorder getLastestOrderTime(){
        Date now = new Date();
        TimeorderExample e = new TimeorderExample();
        TimeorderExample.Criteria c = e.createCriteria();
        c.andIdIsNotNull();
        c.andEndtimeGreaterThan(now);
        e.setOrderByClause("id DESC");
        List<Timeorder> res = timeorderMapper.selectByExample(e);
        if (res != null && res.size() != 0) {
            return res.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Timeorder> getUserOrderInfos(String username){
        Date now =new Date();
        TimeorderExample e = new TimeorderExample();
        TimeorderExample.Criteria c = e.createCriteria();
        c.andUsernameEqualTo(username);
        c.andStarttimeGreaterThan(now);
        return timeorderMapper.selectByExample(e);
    }

    @Override
    public List<Timeorder> getUserAllOrders(String username) {
        TimeorderExample e = new TimeorderExample();
        TimeorderExample.Criteria c = e.createCriteria();
        c.andUsernameEqualTo(username);
        return timeorderMapper.selectByExample(e);
    }

    @Override
    public Timeorder getNowOrder() {
        Date now = new Date();
        TimeorderExample e = new TimeorderExample();
        TimeorderExample.Criteria c = e.createCriteria();
        c.andStarttimeLessThanOrEqualTo(now);
        c.andEndtimeGreaterThan(now);
        List<Timeorder> res = timeorderMapper.selectByExample(e);
        return res.size() == 0 ? null : res.get(0);
    }

    @Override
    public List<Timeorder> getTenDaysOrder() {
        Date now = new Date();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DATE,10);
        Date tenDays = cal.getTime();
        TimeorderExample e = new TimeorderExample();
        TimeorderExample.Criteria c = e.createCriteria();
        c.andStarttimeGreaterThanOrEqualTo(now);
        c.andEndtimeLessThanOrEqualTo(tenDays);
        c.andStatusIn(new ArrayList<Integer>() {{ // 只返回已经预约成功的信息
            add(ORDER_SUCCESS);
        }});
        return timeorderMapper.selectByExample(e);
    }

    @Override
    public boolean canOrderNode(Date startTime, Date endTime) {
        if (startTime.after(endTime)) {
            return false;
        }
        TimeorderExample example1 = new TimeorderExample();
        TimeorderExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andStarttimeGreaterThan(startTime);
        criteria1.andStarttimeLessThan(endTime);
        List<Timeorder> order1 = timeorderMapper.selectByExample(example1);
        TimeorderExample example2 = new TimeorderExample();
        TimeorderExample.Criteria criteria2 = example2.createCriteria();
        criteria2.andEndtimeGreaterThan(startTime);
        criteria2.andEndtimeLessThan(endTime);
        List<Timeorder> order2 = timeorderMapper.selectByExample(example2);
        TimeorderExample example3 = new TimeorderExample();
        TimeorderExample.Criteria criteria3 = example3.createCriteria();
        criteria3.andStarttimeEqualTo(startTime);
        criteria3.andEndtimeEqualTo(endTime);
        List<Timeorder> order3 = timeorderMapper.selectByExample(example3);
        boolean res = (order1 == null || order1.size() == 0) && (order2 == null || order2.size() == 0);
        if (order3 != null && order3.size() > 0 ) {
            res = false;
        }
        return res; // 间隔之间没有任何预约，才可以预约
    }

    @Override
    public boolean orderNode(String username, Date startTime, Date endTime) {
        // 提前判断canUse && 日期合法性
        Timeorder record = new Timeorder();
        record.setUsername(username);
        record.setStarttime(startTime);
        record.setEndtime(endTime);
        // record.setStatus(ORDER_VERIFY); // 待审核
        record.setStatus(ORDER_SUCCESS); // TODO 方便调试
        return timeorderMapper.insertSelective(record) > 0;
    }

    @Override
    public List<Object> canAccessNodeAtTime(String username) {
        Date now = new Date();
        List<Object> res = new ArrayList<>();
        TimeorderExample timeorderExample = new TimeorderExample();
        TimeorderExample.Criteria criteria = timeorderExample.createCriteria();
        criteria.andStarttimeLessThanOrEqualTo(now);
        criteria.andEndtimeGreaterThan(now);
        criteria.andUsernameEqualTo(username);
        criteria.andStatusEqualTo(ORDER_SUCCESS);
        List<Timeorder> ordersRes = timeorderMapper.selectByExample(timeorderExample);
        if (ordersRes == null || ordersRes.size() == 0) {
            res.add(false);
        } else {
            res.add(true);
            res.add(ordersRes.get(0).getEndtime());
        }
        return res;
    }

    @Override
    public List<Integer> cancelOrderNode(String username, List<Integer> orderIds) {
        List<Timeorder> orderRes = getUserOrderInfos(username);
        List<Integer> orderIdRes = new ArrayList<>();
        for (Timeorder item : orderRes) {
            orderIdRes.add(item.getId());
        }
        List<Integer> cancelError = new ArrayList<>();
        for (Integer item : orderIds) {
            if (orderIdRes.contains(item)) { // 可以取消预约
                TimeorderExample e = new TimeorderExample();
                TimeorderExample.Criteria c = e.createCriteria();
                c.andIdEqualTo(item);
                if (timeorderMapper.deleteByExample(e) != 1) {
                    cancelError.add(item);
                }
            }
        }
        return cancelError.size() == 0 ? null : cancelError;
    }

    @Override
    public List<Integer> updateOrderStatus(String username, List<Integer> orderIds) {
        List<Timeorder> orderRes = getUserOrderInfos(username);
        List<Integer> orderIdRes = new ArrayList<>();
        for (Timeorder item : orderRes) {
            orderIdRes.add(item.getId());
        }
        List<Integer> passError = new ArrayList<>();
        for (Integer item : orderIds) {
            if (orderIdRes.contains(item)) { // 可以通过预约
                Timeorder record = new Timeorder();
                record.setId(item);
                record.setStatus(ORDER_SUCCESS);
                if (timeorderMapper.updateByPrimaryKeySelective(record) != 1) {
                    passError.add(item);
                }
            }
        }
        return passError.size() == 0 ? null : passError;
    }

    @Override
    public boolean stopUseNode(int node_id, String username){
        Date now = new Date();
        TimeorderExample e = new TimeorderExample();
        TimeorderExample.Criteria c = e.createCriteria();
//TO DO        c.andNodeidEqualTo(node_id);
        c.andUsernameEqualTo(username);
        c.andStarttimeLessThanOrEqualTo(now);
        c.andEndtimeGreaterThan(now);
        List<Timeorder> orders= timeorderMapper.selectByExample(e);
        if(orders.size() != 1) return false; //出错
        //修改order信息endtime至当前
        Timeorder order = orders.get(0);
        order.setEndtime(now);
        timeorderMapper.updateByExampleSelective(order, e);

        //checkNodeAccessOutDate(node_id);
        return true;
    }

//    @Override
//    @Scheduled(cron = "0 * * * * ?")//每分钟都执行
//    public void updateNodesAccess(){
//        checkNodeAccessOutDate();
//    }
//
//    public void checkNodeAccessOutDate() {
//        Date now = new Date();
//        TimeorderExample te = new TimeorderExample();
//        TimeorderExample.Criteria tc = te.createCriteria();
//        tc.andUsernameEqualTo(currentUsername);
//        tc.andStarttimeLessThanOrEqualTo(now);
//        tc.andEndtimeGreaterThan(now);
//        if(timeorderMapper.countByExample(te) == 0) { // 权限超时
//            //TO DO 擦除所有 juraService.eraseNode();
//            //TO DO 修改nodeinfo的username字段吗 nodeinfoMapper.updateByPrimaryKey(node);
//        }
//    }

/*    @Override
    public void checkNodeAccessNewOwner() {
        NodeinfoExample ne = new NodeinfoExample();
        NodeinfoExample.Criteria nc = ne.createCriteria();
        nc.andIdEqualTo(node_id);
        List<Nodeinfo> nodes = nodeinfoMapper.selectByExample(ne);
        if(nodes.size() != 1) return;
        Nodeinfo node = nodes.get(0);
        if(node.getUsername() != null) return;
        Date now = new Date();
        TimeorderExample te = new TimeorderExample();
        TimeorderExample.Criteria tc = te.createCriteria();
        //TO DO   c.andNodeidEqualTo(node_id);
        tc.andStarttimeLessThanOrEqualTo(now);
        tc.andEndtimeGreaterThan(now);
        List<Timeorder> orders = timeorderMapper.selectByExample(te);
        if(orders.size() == 1) {
            node.setUsername(orders.get(0).getUsername());
            nodeinfoMapper.updateByPrimaryKey(node);
        }
    }*/

    @Override
    public List<Nodeinfo> getNodeInfo() {
        NodeinfoExample ne = new NodeinfoExample();
        NodeinfoExample.Criteria nc = ne.createCriteria();
        nc.andIdIsNotNull();
        return nodeinfoMapper.selectByExample(ne);
    }

    @Override
    public List<Gatewayinfo> getAllGateways() {
        GatewayinfoExample e = new GatewayinfoExample();
        GatewayinfoExample.Criteria c = e.createCriteria();
        c.andIdIsNotNull();
        return gatewayinfoMapper.selectByExample(e);
    }
}
