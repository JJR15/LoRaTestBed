package com.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.project.WebInfoclass.WebNodeOrder;
import com.project.model.*;
import com.project.service.JuraService;
import com.project.service.NodeOrderService;
import com.project.service.OutNodeService;
import com.project.service.SystemInfoService;
import com.project.util.CONSTANT;
import com.project.util.DatePeriod;
import com.project.util.FileUtil;
import com.project.util.updatebin.updateMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.project.util.CONSTANT.*;

@Controller
@SessionAttributes({"username", "currentUser"})
public class NodeOrderController {
    @Autowired
    private NodeOrderService nodeOrderService;

    @Autowired
    private OutNodeService outNodeService;

    @Autowired
    private JuraService juraService;

    @Autowired
    private SystemInfoService systemInfoService;

    //获取节点预订信息
    @RequestMapping(value = "node/orderinfo",method = RequestMethod.GET)
    @ResponseBody
    public List<WebNodeOrder> getNodeOrders(@RequestParam("id") int id){
        List<Timeorder> os= null; //TODO nodeOrderService.getOrderedNodeTime(id);
        List<WebNodeOrder> orders = new ArrayList<>();
        for(Timeorder o:os) {
            orders.add(systemInfoService.toWebNodeOrder(o));
        }
        return orders;
    }

    // 获取节点信息
    @RequestMapping(value = "order/getallnode", method = RequestMethod.GET)
    public
    @ResponseBody
    String getAllNodes() {
        Map<String, Object> res = new HashMap<>();
        res.put("code", -1);
        List<Integer> idList = new ArrayList<>();
        List<String> macList = new ArrayList<>();
        List<Integer> statusList = new ArrayList<>();
        List<Double> longitude_position = new ArrayList<>();
        List<Double> latitude_position = new ArrayList<>();
        List<Nodeinfo> nodeinfos = nodeOrderService.getAllNodes();
        if (nodeinfos != null && nodeinfos.size() != 0) {
            res.put("code", 1);
            for (Nodeinfo item : nodeinfos) {
                idList.add(item.getId());
                macList.add(item.getPiMac());
                statusList.add(item.getState());
                longitude_position.add(item.getLongitude());
                latitude_position.add(item.getLatitude());
            }
            res.put("nodeId", idList);
            res.put("MAC", macList);
            res.put("status", statusList);
            res.put("longitude_position", longitude_position);
            res.put("latitude_position", latitude_position);
        }

        //加入gateway info
        List<Integer> g_idList = new ArrayList<>();
        List<String> g_macList = new ArrayList<>();
        List<Integer> g_statusList = new ArrayList<>();
        List<Double> g_longitude_position = new ArrayList<>();
        List<Double> g_latitude_position = new ArrayList<>();
        List<Integer> g_frequencyList = new ArrayList<>();
        List<Gatewayinfo> gatewayinfos = nodeOrderService.getAllGateways();
        Map<String, Object> gatewaymap = new HashMap<>();
        if (gatewayinfos != null && gatewayinfos.size() != 0) {
            for (Gatewayinfo item : gatewayinfos) {
                g_idList.add(item.getId());
                g_macList.add(item.getPiMac());
                g_statusList.add(item.getStatus());
                g_longitude_position.add(item.getLongitude());
                g_latitude_position.add(item.getLatitude());
                g_frequencyList.add(item.getFrequency());
            }
            gatewaymap.put("gatewayId", g_idList);
            gatewaymap.put("MAC", g_macList);
            gatewaymap.put("status", g_statusList);
            gatewaymap.put("longitude_position", g_longitude_position);
            gatewaymap.put("latitude_position", g_latitude_position);
            gatewaymap.put("frequency",g_frequencyList);
        }
        res.put("gatewayList",gatewaymap);
        return JSON.toJSONString(res);
    }

    // 获取室外节点信息
    @RequestMapping(value = "/order/getoutallnode", method = RequestMethod.GET)
    public
    @ResponseBody
    String getAllOutNodes() {
        Map<String, Object> res = new HashMap<>();
        res.put("code", -1);
        List<Integer> idList = new ArrayList<>();
        List<String> macList = new ArrayList<>();
        List<Integer> statusList = new ArrayList<>();
        List<Double> longitude_position = new ArrayList<>();
        List<Double> latitude_position = new ArrayList<>();
        List<OutNode> outNodeList = outNodeService.getAllOutNodes();
        if(outNodeList != null && outNodeList.size() != 0){
            res.put("code", 1);
            for (OutNode item : outNodeList) {
                idList.add(item.getId());
                macList.add(item.getDev_eui());
                //statusList.add(item.getState());
                statusList.add(1);
                longitude_position.add(item.getLongitude());
                latitude_position.add(item.getLatitude());
            }
            res.put("nodeId", idList);
            res.put("MAC", macList);
            res.put("status", statusList);
            res.put("longitude_position", longitude_position);
            res.put("latitude_position", latitude_position);
        }

        //加入gateway info
        List<Integer> g_idList = new ArrayList<>();
        List<String> g_macList = new ArrayList<>();
        List<Integer> g_statusList = new ArrayList<>();
        List<Double> g_longitude_position = new ArrayList<>();
        List<Double> g_latitude_position = new ArrayList<>();
        List<Integer> g_frequencyList = new ArrayList<>();
        List<Gatewayinfo> gatewayinfos = nodeOrderService.getAllGateways();
        Map<String, Object> gatewaymap = new HashMap<>();
        if (gatewayinfos != null && gatewayinfos.size() != 0) {
            for (Gatewayinfo item : gatewayinfos) {
                g_idList.add(item.getId());
                g_macList.add(item.getPiMac());
                g_statusList.add(item.getStatus());
                g_longitude_position.add(item.getLongitude());
                g_latitude_position.add(item.getLatitude());
                g_frequencyList.add(item.getFrequency());
            }
            gatewaymap.put("gatewayId", g_idList);
            gatewaymap.put("MAC", g_macList);
            gatewaymap.put("status", g_statusList);
            gatewaymap.put("longitude_position", g_longitude_position);
            gatewaymap.put("latitude_position", g_latitude_position);
            gatewaymap.put("frequency",g_frequencyList);
        }
        res.put("gatewayList",gatewaymap);
        return JSON.toJSONString(res);
    }

    @RequestMapping(value = "node/getOrderByUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String getOrderByUser(@ModelAttribute("username") String username) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        List<Timeorder> orders = nodeOrderService.getUserOrderInfos(username);
        List<Object> orderList = new ArrayList<>();
        if (orders != null && orders.size() != 0) {
            for (Timeorder order : orders) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", order.getId());
                item.put("start_time", order.getStarttime());
                item.put("end_time", order.getEndtime());
                item.put("status", order.getStatus());
                orderList.add(item);
            }
        }
        res.put("code", 1);
        res.put("order_list", orderList);
        return JSON.toJSONString(res);
    }

    // 获取节点信息
    @RequestMapping(value = "order/nodestatus", method = RequestMethod.GET)
    public
    @ResponseBody
    String getNodeStatus() {
        Map<String, Object> res = new HashMap<>();
        res.put("code", -1);
        List<Nodeinfo> nodeinfos = nodeOrderService.getAllNodes();
        int NODE_OFFLINE_COUNT = 0;
        int NODE_CLEAN_COUNT = 0;
        int NODE_RUNNING_COUNT = 0;
        int NODE_IDLE_COUNT = 0;
        int NODE_BURNING_COUNT = 0;
        if (nodeinfos != null && nodeinfos.size() != 0) {
            res.put("code", 1);
            for (Nodeinfo item : nodeinfos) {
                if (item.getState() == NODE_OFFLINE) {
                    NODE_OFFLINE_COUNT++;
                } else if (item.getState() == NODE_CLEAN) {
                    NODE_CLEAN_COUNT++;
                } else if (item.getState() == NODE_RUNNING) {
                    NODE_RUNNING_COUNT++;
                } else if (item.getState() == NODE_IDLE) {
                    NODE_IDLE_COUNT++;
                } else if (item.getState() == NODE_BURNING) {
                    NODE_BURNING_COUNT++;
                }
            }
            res.put("NODE_OFFLINE", NODE_OFFLINE_COUNT);
            res.put("NODE_CLEAN", NODE_CLEAN_COUNT);
            res.put("NODE_RUNNING", NODE_RUNNING_COUNT);
            res.put("NODE_IDLE", NODE_IDLE_COUNT);
            res.put("NODE_BURNING", NODE_BURNING_COUNT);
        }
        return JSON.toJSONString(res);
    }

    @RequestMapping(value = "order/getorderorigin", method = RequestMethod.POST)
    public
    @ResponseBody
    String getOrdersOrigin() {
        Map<String, Object> res = new HashMap<>();
        res.put("code", -1);
        List<Object> orderList = new ArrayList<>();
        List<Timeorder> orders = nodeOrderService.getTenDaysOrder();
        if (orders != null && orders.size() != 0) {
            for (Timeorder order : orders) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", order.getId());
                item.put("start_time", order.getStarttime());
                item.put("end_time", order.getEndtime());
                item.put("status", order.getStatus());
                orderList.add(item);
            }
            res.put("code", 1);
        }
        res.put("order_list", orderList);
        return JSON.toJSONString(res);
    }


    @RequestMapping(value = "order/getorder", method = RequestMethod.POST)
    public
    @ResponseBody
    String getOrders(@ModelAttribute("username") String username) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", -1);
        List<Object> orderList = new ArrayList<>();
        List<Timeorder> orders = nodeOrderService.getTenDaysOrder();
        Map<Date, Date> dateSection = DatePeriod.generateSection();
        if (orders != null && orders.size() != 0) {
            for (Timeorder order : orders) {
                Date keyItem = order.getStarttime();
                String str = String.valueOf(keyItem.getTime() / 1000);
                Iterator<Map.Entry<Date, Date>> iterator = dateSection.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Date, Date> entry = iterator.next();
                    String originStr = String.valueOf(entry.getKey().getTime() / 1000);
                    if (str.equals(originStr)) {
                        iterator.remove();
                        System.out.println("remove");
                        Map<Object, Object> item = new HashMap<>();
                        //TODO 把key变成000ms
                        item.put("start_time", entry.getKey());
                        item.put("end_time", entry.getValue());
                        if (order.getUsername().equals(username)) {
                            item.put("status", 4); // 被预订
                        } else {
                            item.put("status", 3); // 其他人预订
                        }
                        orderList.add(item); // 被预约的也返回过去
                    }
                }
            }
        }
        for (Map.Entry<Date, Date> entry : dateSection.entrySet()) {
            Map<Object, Object> item = new HashMap<>();
            //TODO 把key变成000ms
            item.put("start_time", entry.getKey());
            item.put("end_time", entry.getValue());
            item.put("status", 0); // 可以预订
            orderList.add(item);
            // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        if (orderList.size() != 0) {
            res.put("code", 1);
        }
        res.put("order_list", orderList);
        // 统计已经预约的总次数
        List<Timeorder> myOrders = nodeOrderService.getUserAllOrders(username);
        int count = 0;
        List<Object> myOrderList = new ArrayList<>();
        if (myOrders != null && myOrders.size() != 0) {
            for (Timeorder order : myOrders) {
                if (order.getStatus() == ORDER_SUCCESS && order.getEndtime().after(new Date())) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("id", order.getId());
                    item.put("start_time", order.getStarttime());
                    item.put("end_time", order.getEndtime());
                    myOrderList.add(item);
                    count++;
                }
            }
        }
        res.put("myorder", myOrderList);
        res.put("myorder_count", count);
        return JSON.toJSONString(res);
    }

    @RequestMapping(value = "order/getmyorder", method = RequestMethod.POST)
    public
    @ResponseBody
    String getMyOrders(@ModelAttribute("username") String username) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", -1);
        res.put("count", 0);
        List<Object> orderList = new ArrayList<>();
        List<Timeorder> orders = nodeOrderService.getUserAllOrders(username);
        int count = 0;
        if (orders != null && orders.size() != 0) {
            for (Timeorder order : orders) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", order.getId());
                item.put("start_time", order.getStarttime());
                item.put("end_time", order.getEndtime());
                int status = order.getStatus();
                if (order.getEndtime().before(new Date())) {
                    item.put("status", ORDER_OUTTIME);
                } else {
                    item.put("status", order.getStatus());
                }
                orderList.add(item);
                if (status == ORDER_SUCCESS) {
                    count++;
                }
            }
            res.put("code", 1);
            res.put("count", count);
        }
        res.put("order_list", orderList);
        return JSON.toJSONString(res);
    }

    // 批量预定节点
    @RequestMapping(value = "order/new", method = RequestMethod.POST)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public
    @ResponseBody
    String orderNodes(@ModelAttribute("username")String username,
                      @RequestParam("startTime") Date startTime[],
                      @RequestParam("endTime") Date endTime[]) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", -1);
        if (startTime == null || endTime == null ) {
            res.put("res", "time illegal");
            return JSON.toJSONString(res);
        }
        if(startTime.length != endTime.length || startTime.length == 0 || endTime.length == 0){
            res.put("res", "time illegal");
            return JSON.toJSONString(res);
        }
        for(int i=0;i<startTime.length;i++){
            if(startTime[i].before(new Date()) || endTime[i].before(startTime[i])){
                res.put("res", "time illegal");
                return JSON.toJSONString(res);
            }
            if (nodeOrderService.canOrderNode(startTime[i], endTime[i])) {
                boolean insertRes = nodeOrderService.orderNode(username, startTime[i], endTime[i]);
                if (insertRes) {
                    res.put("code", 1);
                    res.put("res", "success");
                } else {
                    res.put("code", 2);
                    res.put("res", "order failed");
                }
            } else {
                res.put("code", 0);
                res.put("res", "time is denied");
            }
        }
        return JSON.toJSONString(res);
    }

    // 用户是否可以使用系统
    @RequestMapping(value = "/order/canuse", method = RequestMethod.POST)
    public
    @ResponseBody
    String canUserSys(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession currentSession = request.getSession();
        if (currentSession == null) {
            res.put("code", -1);
            res.put("res", "please login");
            return JSON.toJSONString(res);
        }
        Object usernameObj = currentSession.getAttribute("username");
        if (usernameObj == null) {
            res.put("code", -1);
            res.put("res", "please login");
            return JSON.toJSONString(res);
        }
        String username = usernameObj.toString();
        if (username == null || username.equals("")) {
            res.put("code", -1);
            res.put("res", "please login");
        } else {
            List<Object> queryRes = nodeOrderService.canAccessNodeAtTime(username);
            if (queryRes.size() == 1) {
                res.put("code", 0);
            } else {
                res.put("code", 1);
                res.put("endtime", queryRes.get(1)); //结束时间
            }
        }
        return JSON.toJSONString(res);
    }

    //取消预定
    @RequestMapping(value = "/order/cancel", method = RequestMethod.POST)
    public
    @ResponseBody
    String cancelOrders(@ModelAttribute("username") String currentUsername,
                        @RequestParam("orderId") List<Integer> orderIds) {
        Map<String, Object> res = new HashMap<>();
        String msg = "";
        List<Integer> errorList = nodeOrderService.cancelOrderNode(currentUsername, orderIds);
        if (errorList != null) {
            for (Integer item : errorList) {
                msg = msg.concat("无法取消预约编号:" + item + "的预约\n");
            }
        }
        if (msg.isEmpty()) {
            res.put("code", 1);
            res.put("res", "success");
        } else {
            res.put("code", 0);
            res.put("res", msg);
        }
        return JSON.toJSONString(res);
    }

    // 审批预订
    @RequestMapping(value = "/order/pass", method = RequestMethod.POST)
    public
    @ResponseBody
    String passOrders(@ModelAttribute("username") String currentUsername,
                        @RequestParam("orderId[]") List<Integer> orderIds) {
        Map<String, Object> res = new HashMap<>();
        String msg = "";
        List<Integer> errorList = nodeOrderService.updateOrderStatus(currentUsername, orderIds);
        if (errorList != null) {
            for (Integer item : errorList) {
                msg = msg.concat("无法通过预约编号:" + item + "的预约\n");
            }
        }
        if (msg.isEmpty()) {
            res.put("code", 1);
            res.put("res", "success");
        } else {
            res.put("code", 0);
            res.put("res", msg);
        }
        return JSON.toJSONString(res);
    }

    //停止使用 [id]
    @RequestMapping(value = "/stopUse",method = RequestMethod.POST)
    @ResponseBody
    public String stopUseNodes(@RequestParam("ids[]") List<Integer> ids,
                               @ModelAttribute("username") String username){
        String res="";
        for(int id:ids){
            Timeorder nowOrder = nodeOrderService.getNowOrder();
            if(nowOrder == null || !nowOrder.getUsername().equals(username)){
                res = res.concat("没有节点"+id+"的权限\n");
                continue;
            }
            if(!nodeOrderService.stopUseNode(id, username)){
                res = res.concat("无法停止节点"+id+"\n");
            }
        }
        if(res.isEmpty()) res="success";
        return res;
    }

    //节点操作([id], op)  op in [开启， 停止，重启]
    @RequestMapping(value = "/node/operate", method = RequestMethod.POST)
    public
    @ResponseBody
    String nodeOperates(@RequestParam("ids[]") List<Integer> ids,
                        @RequestParam("opt") int opSign,
                        @ModelAttribute("username") String username) {
        Map<String, Object> res = new HashMap<>();
        Timeorder nowOrder = nodeOrderService.getNowOrder();
        if (nowOrder == null || !nowOrder.getUsername().equals(username)) {
            res.put("code", -1);
        } else {
            try {
                if (opSign == 0) {
                    juraService.startNode(ids);
                } else if (opSign == 1) {
                    juraService.stopNode(ids);
                } else if (opSign == 2) {
                    juraService.restartNode(ids);
                }
            } catch (Exception e) {
                res.put("code", 0);
                e.printStackTrace();
                return JSON.toJSONString(res);
            }
        }
        juraService.showinfo();
        res.put("code", 1);
        System.out.println("opt :"+ opSign);
        return JSON.toJSONString(res);
    }

    //节点烧录 [id]
    @RequestMapping(value = "/node/burn", method = RequestMethod.POST)
    @ResponseBody
    public String nodeBurn(@RequestParam("ids") String node_ids,
                           @RequestParam("file") MultipartFile bin_file,
                           @RequestParam("ischange") Integer isChange,
                           @ModelAttribute("username") String username) {
        String[] s_ids = node_ids.split(",");
        Map<String, Object> res = new HashMap<>();

        Timeorder nowOrder = nodeOrderService.getNowOrder();
        if (nowOrder == null || !nowOrder.getUsername().equals(username)) {
            res.put("code", -1);
            res.put("res", "权限不足");
        } else {
            if (!bin_file.getOriginalFilename().endsWith(".bin")) {
                res.put("code", -1);
                res.put("res", "请上传.bin文件");
                return JSON.toJSONString(res);
            }
            if (bin_file.getSize() > 1048576) {
                res.put("code", -1);
                res.put("res", "文件长度过大");
                return JSON.toJSONString(res);
            }
            //change bin
            if (isChange == 1) {
                String newFileName = CONSTANT.FILE_PATH + File.separator + "burn" + File.separator + bin_file.getOriginalFilename();
                File tmp_bin_file = new File(newFileName);
                try {
                    bin_file.transferTo(tmp_bin_file);
                } catch (Exception e) {
                    e.printStackTrace();
                    return e.getMessage();
                }
                String locRes = updateMain.updateBin(newFileName, CONSTANT.pattern);
                if (locRes.contains("Error")) {
                    return locRes;
                } else {
                    int loc = Integer.parseInt(locRes);
                    if (loc == 0) {
                        return "定位修改位置失败";
                    }
                }
            }
            byte[] file_data;
            try {
                file_data = bin_file.getBytes();
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
            //TODO 将loc加到传输数据中
            List<Integer> ids = new ArrayList<>();
            for (String s_id : s_ids) {
                int id = Integer.parseInt(s_id);
                ids.add(id);
            }
            juraService.burnNode(ids, bin_file.getOriginalFilename(), file_data);
            res.put("code", 1);
            res.put("res", "burn success");
        }
        return JSON.toJSONString(res);
    }

    @RequestMapping(value = "/node/log",method = RequestMethod.POST)
    @ResponseBody
    public String getLog(@RequestParam("id") int node_id,
                         @RequestParam(value = "starttime", required = false) Date starttime,
                         @RequestParam(value = "endtime", required = false) Date endtime,
                         @ModelAttribute("username") String username) {
        //juraService.getLogFile(node_id, starttime, endtime);
        juraService.setSendData(node_id, true);
        return "success";
    }

    @RequestMapping(value = "node/data",method = RequestMethod.POST)
    @ResponseBody
    public String getData(@RequestParam("id") int node_id,
                          @RequestParam("starttime") Date starttime,
                          @RequestParam("endtime") Date endtime,
                          @ModelAttribute("username") String username) {
        Timeorder nowOrder = nodeOrderService.getNowOrder();
        if(nowOrder == null || !nowOrder.getUsername().equals(username))
            return "no access to node " + node_id;
        //修正时间
        //Timeorder order = nodeOrderService.getOrder(node_id, username);
        //if(starttime.before(order.getStarttime()))
        //    starttime = order.getStarttime();
        Date now = new Date();
        if(endtime.after(now))
            endtime = now;
        juraService.getDataFile(node_id, starttime, endtime,false);
        return "success";
    }

    @RequestMapping(value = "/node/datas", method = RequestMethod.POST)
    @ResponseBody
    public String getDatas(@RequestParam("ids[]") List<Integer> ids,
                           @RequestParam("starttime") Date starttime,
                           @RequestParam("endtime") Date endtime,
                           @ModelAttribute("username") String username) {
        Map<String, Object> res = new HashMap<>();
        Timeorder myOrder = nodeOrderService.getNowOrder();
        if (myOrder == null || !myOrder.getUsername().equals(username)) { // 没有预约权限
            res.put("code", -1);
            res.put("res", "no access to node");
            return JSON.toJSONString(res);
        }
        Date order_start_time = myOrder.getStarttime();
        int count = 0;
        int size = ids.size();
        //修正时间
        for (int id : ids) {
            if (starttime.before(order_start_time))
                starttime = order_start_time;
            Date now = new Date();
            if (endtime.after(now))
                endtime = now;
            juraService.getDataFile(id, starttime, endtime, true);
        }
        //while(count < size){
        //    count = juraService.getShouldZipStart();
        //System.out.println(count);
        //}
        try {
            TimeUnit.SECONDS.sleep(3); // 秒
        } catch (Exception e) {
            e.printStackTrace();
            res.put("code", 0);
            res.put("res", e.getMessage());
            return JSON.toJSONString(res);
        }
        String zipFileName = CONSTANT.FILE_PATH + File.separator + "tmp.zip";
        if (size != 0)
            juraService.getZipFile(zipFileName, ids.get(0));
        else {
            res.put("code", 0);
            res.put("res", "fail");
            return JSON.toJSONString(res);
        }
        try {
            TimeUnit.SECONDS.sleep(1);//秒
        } catch (Exception e) {
            e.printStackTrace();
            res.put("code", 0);
            res.put("res", e.getMessage());
            return JSON.toJSONString(res);
        }
        FileUtil.delAllFile(CONSTANT.FILE_PATH + File.separator + "zip");
        FileUtil.delFile(zipFileName);
        //juraService.setShouldZipStart(0);
        res.put("code", 1);
        res.put("res", "success");
        return JSON.toJSONString(res);
    }


    //网关操作([id], op)  op in [开启， 停止，重启]
    @RequestMapping(value = "/gateway/operate", method = RequestMethod.POST)
    public
    @ResponseBody
    String gatewayOperates(@RequestParam("ids[]") List<Integer> ids,
                        @RequestParam("opt") int opSign,
                        @ModelAttribute("username") String username) {
        Map<String, Object> res = new HashMap<>();
        Timeorder nowOrder = nodeOrderService.getNowOrder();
        if (nowOrder == null || !nowOrder.getUsername().equals(username)) {
            res.put("code", -1);
        } else {
            try {
                if (opSign == 0) {
                    juraService.startGateway(ids);
                } else if (opSign == 1) {
                    juraService.stopGateway(ids);
                } else if (opSign == 2) {
                    juraService.restartGateway(ids);
                }
            } catch (Exception e) {
                res.put("code", 0);
                e.printStackTrace();
                return JSON.toJSONString(res);
            }
        }
        juraService.showinfo();
        res.put("code", 1);
        System.out.println("opt :"+ opSign);
        return JSON.toJSONString(res);
    }

    //网关频率
    @RequestMapping(value = "/gateway/frequency", method = RequestMethod.POST)
    public
    @ResponseBody
    String gatewayFrequency(@RequestParam("ids[]") List<Integer> ids,
                        @RequestParam("frequency") int frequency,
                        @ModelAttribute("username") String username) {
        Map<String, Object> res = new HashMap<>();
        Timeorder nowOrder = nodeOrderService.getNowOrder();
        if (nowOrder == null || !nowOrder.getUsername().equals(username)) {
            res.put("code", -1);
        } else {
            try {
                juraService.changeGateway(ids,frequency);
            } catch (Exception e) {
                res.put("code", 0);
                e.printStackTrace();
                return JSON.toJSONString(res);
            }
        }
        res.put("code", 1);
        return JSON.toJSONString(res);
    }
}
