package com.project.controller;

import com.project.WebInfoclass.WebNode;
import com.project.WebInfoclass.WebNodeManage;
import com.project.WebInfoclass.WebUserOrder;
import com.project.model.Nodeinfo;
import com.project.model.Timeorder;
import com.project.service.NodeOrderService;
import com.project.service.SystemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
//拦截http://localhost/lora/user
@RequestMapping("/")
@SessionAttributes({"username"})
public class WebController {
    @Autowired
    private NodeOrderService nodeOrderService;

    @Autowired
    private SystemInfoService systemInfoService;


    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String GetIndex(HttpServletRequest request) {
        request.setAttribute("total_user",systemInfoService.getTotal_user());
        request.setAttribute("total_node",systemInfoService.getTotal_node());
        request.setAttribute("running_node",systemInfoService.getRun_node());
        String language = request.getParameter("language");
        if("en".equals(language))
            return "index_en";
        else return "index";
    }

    @RequestMapping(value = "about_us", method = RequestMethod.GET)
    public String GetAboutUs(HttpServletRequest request) {
        String language = request.getParameter("language");
        if("en".equals(language))
            return "about_us_en";
        else return "about_us";
    }

    @RequestMapping(value = "node_map", method = RequestMethod.GET)
    public String GetNodeMap(HttpServletRequest request) {
        String language = request.getParameter("language");
        if("en".equals(language))
            return "airscape_en";
        else return "airscape_en";
    }

    @RequestMapping(value = "gateway", method = RequestMethod.GET)
    public String GetGateway(HttpServletRequest request) {
        String language = request.getParameter("language");
        if("en".equals(language))
            return "gateway_en";
        else return "gateway";
    }

    @RequestMapping(value = "manual", method = RequestMethod.GET)
    public String GetManual(HttpServletRequest request) {
        String language = request.getParameter("language");
        if("en".equals(language))
            return "manual_en";
        else return "manual";
    }

    @RequestMapping(value = "node_manage", method = RequestMethod.GET)
    public String GetNodeManage(HttpServletRequest request, @ModelAttribute("username") String username) {
        List<WebNodeManage> node_manage_infos = new ArrayList<>();
        List<Timeorder> using_nodes = null; //TODO nodeOrderService.getUserAccessNodes(username);
        for(Timeorder o:using_nodes) {
            node_manage_infos.add(systemInfoService.toWebNodeManage(o));
        }
        request.setAttribute("node_manage_infos",node_manage_infos);
        String language = request.getParameter("language");
        if("en".equals(language))
            return "node_manage_en";
        else return "node_manage";
    }

    @RequestMapping(value = "node/info", method = RequestMethod.GET)
    public String GetNodeInfos(HttpServletRequest request) {
        List<Nodeinfo> nodes = systemInfoService.getAllNodes();
        List<WebNode> node_infos = new ArrayList<>();
        for(Nodeinfo node:nodes) {
            node_infos.add(systemInfoService.toWebNode(node));
        }
        //System.out.println(node_infos.size());
        request.setAttribute("node_infos",node_infos);
        String language = request.getParameter("language");
        if("en".equals(language))
            return "node_info_en";
        else return "node_info";
    }

    @RequestMapping(value = "node/order", method = RequestMethod.GET)
    public String GetNodeOrders(HttpServletRequest request,@ModelAttribute("username") String username) {
        List<Timeorder> orders = nodeOrderService.getUserOrderInfos(username);
        List<WebUserOrder> user_orders = new ArrayList<>();
        for(Timeorder order:orders) {
            user_orders.add(systemInfoService.toWebUserOrder(order));
        }
        request.setAttribute("node_orders",user_orders);
        String language = request.getParameter("language");
        if("en".equals(language))
            return "node_order_en";
        else return "node_order";
    }

    @RequestMapping(value = "node/log", method = RequestMethod.GET)
    public String NodeLog(HttpServletRequest request) {
        String s_id = request.getParameter("node");
        request.setAttribute("id",s_id);
        String language = request.getParameter("language");
        if("en".equals(language))
            return "node_log_en";
        else return "node_log";
    }

    @RequestMapping(value = "node/data", method = RequestMethod.GET)
    public String NodeData(HttpServletRequest request) {
        String s_id = request.getParameter("node");
        request.setAttribute("id",s_id);
        String language = request.getParameter("language");
        if("en".equals(language))
            return "node_data_en";
        else return "node_data";
    }
}

