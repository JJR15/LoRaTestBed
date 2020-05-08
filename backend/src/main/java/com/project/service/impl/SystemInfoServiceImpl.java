package com.project.service.impl;

import com.project.WebInfoclass.WebNode;
import com.project.WebInfoclass.WebNodeManage;
import com.project.WebInfoclass.WebNodeOrder;
import com.project.WebInfoclass.WebUserOrder;
import com.project.mapper.NodeinfoMapper;
import com.project.mapper.TimeorderMapper;
import com.project.mapper.UserMapper;
import com.project.model.Nodeinfo;
import com.project.model.NodeinfoExample;
import com.project.model.Timeorder;
import com.project.model.UserExample;
import com.project.service.SystemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("systemInfoServiceImpl")
public class SystemInfoServiceImpl implements SystemInfoService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NodeinfoMapper nodeinfoMapper;

    @Override
    public boolean checkNodeExist(int node_id){
        NodeinfoExample e = new NodeinfoExample();
        NodeinfoExample.Criteria c = e.createCriteria();
        c.andIdEqualTo(node_id);
        return nodeinfoMapper.countByExample(e) == 1;
    }

    @Override
    public Nodeinfo getNodeInfo(int node_id){
        return nodeinfoMapper.selectByPrimaryKey(node_id);
    }


    @Override
    public List<Nodeinfo> getAllNodes(){
        List<Integer> ids = new ArrayList<>();
        NodeinfoExample e = new NodeinfoExample();
        NodeinfoExample.Criteria c = e.createCriteria();
        c.andIdIsNotNull();
        return nodeinfoMapper.selectByExample(e);
    }

    @Override
    public int getTotal_user() {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameIsNotNull();
        return userMapper.countByExample(example);
    }

    @Override
    public int getTotal_node() {
        NodeinfoExample example = new NodeinfoExample();
        NodeinfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        return nodeinfoMapper.countByExample(example);
    }

    @Override
    public int getRun_node() {
        NodeinfoExample example = new NodeinfoExample();
        NodeinfoExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(2);
        return nodeinfoMapper.countByExample(example);
    }

    @Override
    public WebNode toWebNode(Nodeinfo node){
        return new WebNode(node.getId(),node.getPiMac(),node.getState(),node.getLongitude(),node.getLatitude());
    }

    @Override
    public WebNodeManage toWebNodeManage(Timeorder order){
        WebNode wn = null; //TODO = toWebNode(getNodeInfo(order.getNodeid()));
        return new WebNodeManage(wn,order.getStarttime(), order.getEndtime());
    }

    @Override
    public WebUserOrder toWebUserOrder(Timeorder order){
        return null;
//TODO        return new WebUserOrder(order.getNodeid(), order.getStarttime(), order.getEndtime());
    }

    @Override
    public WebNodeOrder toWebNodeOrder(Timeorder order){
        return new WebNodeOrder(order.getUsername(), order.getStarttime(), order.getEndtime());
    }
}
