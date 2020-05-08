package com.project.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.project.model.Nodeinfo;
import com.project.service.NodeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/map")
@SessionAttributes({"username"})
public class MapController {
    @Autowired
    private NodeOrderService nodeOrderService;

    @RequestMapping(value = "initGateway", method = RequestMethod.POST)
    public
    @ResponseBody
    String getGateway() {
        List<List<Double>> res = new ArrayList<>();
        List<Double> subRes = new ArrayList<>(); // 0是经度；1是纬度
        subRes.add(116.3413);
        subRes.add(40.0066);
        res.add(subRes);
        return JSON.toJSONString(res);
    }

    @RequestMapping(value = "nodeinfo", method = RequestMethod.POST)
    public
    @ResponseBody
    String getNodeInfo() {
        List<Nodeinfo> res = nodeOrderService.getNodeInfo();
        if (res == null) {
            res = new ArrayList<>();
        }
        Map<String, Map<String, Double>> map = new HashMap<>();
        for (Nodeinfo nodeinfo : res) {
            Map<String, Double> subMap = new HashMap<>();
            subMap.put("latitude", nodeinfo.getLatitude());
            subMap.put("longitude", nodeinfo.getLongitude());
            map.put("" + nodeinfo.getId(), subMap);
        }
        return JSON.toJSONString(map);
    }
/*
    @RequestMapping(value = "stop", method = RequestMethod.POST)
    public
    @ResponseBody
    String stopNode(@ModelAttribute("username") String username,
                    @RequestParam("nodes") String nodes,
                    @RequestParam("sign") byte sign) {
        String res = "";
        List<Integer> node_ids = new ArrayList<Integer>();
        String[] ns = nodes.split("#");
        for(String n:ns) {
            node_ids.add(Integer.parseInt(n));
        }
        if (burnServiceImpl.stopNodes(username, node_ids, sign)) {
            res = "true";
        } else {
            res = "false";
        }
        return JSON.toJSONString(res);
    }
    */
}
