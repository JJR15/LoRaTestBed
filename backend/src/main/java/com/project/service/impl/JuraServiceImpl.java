package com.project.service.impl;

import com.project.WebInfoclass.WebUserOrder;
import com.project.mapper.GatewayinfoMapper;
import com.project.mapper.NodeinfoMapper;
import com.project.mapper.TimeorderMapper;
import com.project.model.*;
import com.project.service.JuraService;
import com.project.service.NodeOrderService;
import com.project.util.CONSTANT;
import com.project.util.MyMap;
import com.project.util.ZipUtil;
import io.socket.client.Ack;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.socket.emitter.Emitter;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import javax.xml.soap.Node;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.project.util.CONSTANT.*;


@Service("juraServiceImpl")
public class JuraServiceImpl implements JuraService {

    @Autowired
    private NodeOrderService nodeOrderService;
    @Autowired
    private NodeinfoMapper nodeinfoMapper;
    @Autowired
    private GatewayinfoMapper gatewayinfoMapper;
    @Autowired
    private TimeorderMapper timeorderMapper;
    private static MyMap<WebSocketSession,String> node_message_sessions = new MyMap<WebSocketSession,String>();
    private static MyMap<WebSocketSession,Integer> node_data_sessions = new MyMap<WebSocketSession,Integer>();
    private static HashMap<Integer, File> log_file_map = new HashMap<Integer, File>();
    private static HashMap<Integer, File> data_file_map = new HashMap<Integer, File>();
    private static Socket socket;
    private static int shouldZipStart = 0;

    @Override
    public void connectJuraServer() throws Exception{
        IO.Options opts = new IO.Options();
        opts.query = CONSTANT.JURA_QUERY;
        //opts.forceNew = true;
        //opts.timeout = -1000;
        socket = IO.socket(CONSTANT.JURA_URI, opts);
        //来自jura server
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                showinfo();
                //System.out.println("socket show !");
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                System.out.println("jura socket disconnect:" + new Date());
                //socket.close();
            }

        }).on("nodedata", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                int id = (int) args[0];
                String data = (String) args[1];
                //System.out.println("nodedata " + id + " "+data);
                // Set<WebSocketSession> sessions = getDataSession(id);
                Set<WebSocketSession> sessions = getMessageSession(id);
                try {
                    for (WebSocketSession socketSession : sessions) {
                        socketSession.sendMessage(new TextMessage("begin send data"));
                        socketSession.sendMessage(new TextMessage("nodedata " + id + " " + data));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).on("update", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                JSONObject node = (JSONObject)args[0];
                //System.out.println("update "+node.toString());
                try{
                    int id = node.getInt("nid");
                    String s_state = node.getString("state");
                    int state = 0;
                    switch (s_state) {
                        case "OFFLINE": state = 0; break;
                        case "CLEAN": state = 1; break;
                        case "RUNNING": state = 2; break;
                        case "IDLE": state = 3; break;
                        case "BURNING": state = 4; break;
                        default:break;
                    }

                    String ip = node.getString("ip");
                    String lat = node.getString("lat");
                    String lng = node.getString("lng");
                    NodeinfoExample e = new NodeinfoExample();
                    NodeinfoExample.Criteria c = e.createCriteria();
                    c.andIdEqualTo(id);
                    List<Nodeinfo> n_list = nodeinfoMapper.selectByExample(e);
                    if(n_list.size()!=1) return;
                    Nodeinfo n = n_list.get(0);
                    if(n.getState()!=state){
                        Set<WebSocketSession> sessions = getMessageSession(id);
                        try{
                            for(WebSocketSession socketSession:sessions) {
                                socketSession.sendMessage(new TextMessage("status change "+id + " " + state));
                            }
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    n.setState(state);
                    if(!ip.isEmpty()) n.setIp(ip);
                    if(!lat.isEmpty()) n.setLatitude(Double.parseDouble(lat));
                    if(!lng.isEmpty()) n.setLongitude(Double.parseDouble(lng));
                    nodeinfoMapper.updateByPrimaryKey(n);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }

            }

        }).on("start node message", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                int id = (int) args[1];
                String message = (String) args[0];
                if(args.length>2) message = message + args[2];

                //System.out.println("start node message" + id +" "+ message);

                Set<WebSocketSession> sessions = getMessageSession(id);
                try{
                    for(WebSocketSession socketSession:sessions) {
                        socketSession.sendMessage(new TextMessage("start message "+id + " " + message));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).on("stop node message", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                int id = (int) args[1];
                String message = (String) args[0];
                if(args.length>2) message = message + args[2];

                //System.out.println("start node message" + id +" "+ message);

                Set<WebSocketSession> sessions = getMessageSession(id);
                try{
                    for(WebSocketSession socketSession:sessions) {
                        socketSession.sendMessage(new TextMessage("stop message "+id + " " + message));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).on("restart node message", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                int id = (int) args[1];
                String message = (String) args[0];
                if(args.length>2) message = message + args[2];

                //System.out.println("start node message" + id +" "+ message);

                Set<WebSocketSession> sessions = getMessageSession(id);
                try{
                    for(WebSocketSession socketSession:sessions) {
                        socketSession.sendMessage(new TextMessage("restart message "+id + " " + message));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).on("burn node message", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                int id = (int) args[1];
                String message = (String) args[0];
                if(args.length>2) message = message + args[2];

                //System.out.println("start node message" + id +" "+ message);

                Set<WebSocketSession> sessions = getMessageSession(id);
                try{
                    for(WebSocketSession socketSession:sessions) {
                        socketSession.sendMessage(new TextMessage("burn message "+id + " " + message));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).on("progress bar", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                int id = (int) args[0];
                String percent = (String) args[1];
                //System.out.println("burn progress "+id+" "+percent);

                Set<WebSocketSession> sessions = getMessageSession(id);
                try{
                    for(WebSocketSession socketSession:sessions) {
                        socketSession.sendMessage(new TextMessage("burn progress "+id + " " + percent));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        /*socket.on("node log", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                int id = (int) args[0];
                String log = (String) args[1];
                Set<WebSocketSession> sessions = getLogSession(id);
                try{
                    for(WebSocketSession socketSession:sessions) {
                        socketSession.sendMessage(new TextMessage("log "+id + " " + log));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).on("log file", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                int id = (int) args[0];
                String file_name = (String) args[1];
                boolean is_end = (boolean) args[2];
                byte[] file_data = (byte[]) args[3];
                File file;
                if(log_file_map.containsKey(id)){
                    file = log_file_map.get(id);
                }
                else {
                    file = createFile(CONSTANT.FILE_PATH + "\\log\\" + id, file_name);
                    log_file_map.put(id, file);
                }
                writeFile(file, file_data, true);

                if(is_end){
                    Set<WebSocketSession> sessions = getMessageSession(id);
                    try{
                        for(WebSocketSession session:sessions){
                            session.sendMessage(new TextMessage("log file " + file.getPath()));
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }

        });*/

        //socket.open();
        socket.connect();
        //socket.on("ping", sendPong);
        System.out.println("socket open !");
    }
    public void showinfo() {
        socket.emit("show", new Ack() {
            @Override
            public void call(Object... objects) {
                JSONArray arr =(JSONArray)objects[1];
                //System.out.println("nodeinfos "+arr.toString());
                List<Integer> update_nodes_ids = new ArrayList<>();
                // TODO
                try {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject node = arr.getJSONObject(i);
                        String mac = node.getString("mac");
                        int nid = node.getInt("nid");
                        String s_state = node.getString("state");
                        int state = 0;
                        switch (s_state) {
                            case "OFFLINE": state = NODE_OFFLINE; break;
                            case "CLEAN": state = NODE_CLEAN; break;
                            case "RUNNING": state = NODE_RUNNING; break;
                            case "IDLE": state = NODE_IDLE; break;
                            case "BURNING": state = NODE_BURNING; break;
                            default:break;
                        }
                        String ip = node.getString("ip");
                        String lat = node.getString("lat");
                        String lng = node.getString("lng");


                        update_nodes_ids.add(nid);
                        // 查找mac一样的节点记录，并更新数据表
                        NodeinfoExample e = new NodeinfoExample();
                        NodeinfoExample.Criteria c = e.createCriteria();
                        c.andPiMacEqualTo(mac);
                        List<Nodeinfo> old_nodes = nodeinfoMapper.selectByExample(e);
                        if (old_nodes.size() == 1) {
                            Nodeinfo oldn = old_nodes.get(0);
                            if (oldn.getId() != nid) { // Id编号不相符
                                TimeorderExample example = new TimeorderExample();
                                TimeorderExample.Criteria criteria = example.createCriteria();
                                criteria.andIdIn(new ArrayList<Integer>(){{add(oldn.getId()); add(nid);}});
                                //TODO 特殊情况 = timeorderMapper.deleteByExample(example);
                                nodeinfoMapper.deleteByPrimaryKey(nid);
                                oldn.setId(nid); }oldn.setState(state);
                            if (!ip.isEmpty()) oldn.setIp(ip);
                            if (!lat.isEmpty()) oldn.setLatitude(Double.parseDouble(lat));
                            if (!lng.isEmpty()) oldn.setLongitude(Double.parseDouble(lng));
                            nodeinfoMapper.updateByExample(oldn, e); } else { //创建新节点
                            TimeorderExample example = new TimeorderExample();
                            TimeorderExample.Criteria criteria = example.createCriteria();
                            criteria.andIdEqualTo(nid);
                            //TODO 特殊情况 = timeorderMapper.deleteByExample(example);
                            nodeinfoMapper.deleteByPrimaryKey(nid);
                            Nodeinfo new_node = new Nodeinfo(nid, mac, state, null, NODE_LONGITUDE, NODE_INIT_LATITUDE, ip);
                            if (!lat.isEmpty()) new_node.setLatitude(Double.parseDouble(lat));
                            if (!lng.isEmpty()) new_node.setLongitude(Double.parseDouble(lng));
                            nodeinfoMapper.insert(new_node); }

                    }
                    // 删除无效节点
                    TimeorderExample e1 = new TimeorderExample();
                    TimeorderExample.Criteria c1 = e1.createCriteria();
                    c1.andIdNotIn(update_nodes_ids);
                    //TODO 特殊情况 = timeorderMapper.deleteByExample(e1);

                    NodeinfoExample e2 = new NodeinfoExample();
                    NodeinfoExample.Criteria c2 = e2.createCriteria();
                    c2.andIdNotIn(update_nodes_ids);
                    nodeinfoMapper.deleteByExample(e2);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
                //System.out.println("socket show Acked!");
            }
        });
        socket.emit("show gateways", new Ack(){
            @Override
            public void call(Object... objects) {
                JSONArray arr =(JSONArray)objects[1];
                //System.out.println("gatewayinfos "+arr.toString());
                List<Integer> update_gateway_ids = new ArrayList<>();
                try {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject gateway = arr.getJSONObject(i);
                        String mac = gateway.getString("mac");
                        int nid = gateway.getInt("nid");
                        String s_state = gateway.getString("state");
                        int state = 0;
                        switch (s_state) {
                            case "OFFLINE": state = NODE_OFFLINE; break;
                            case "CLEAN": state = NODE_CLEAN; break;
                            case "RUNNING": state = NODE_RUNNING; break;
                            case "IDLE": state = NODE_IDLE; break;
                            case "BURNING": state = NODE_BURNING; break;
                            default:break;
                        }
                        String ip = gateway.getString("ip");
                        String lat = gateway.getString("lat");
                        String lng = gateway.getString("lng");
                        int frequency = gateway.getInt("frequency");

                        update_gateway_ids.add(nid);
                        // 查找mac一样的节点记录，并更新数据表
                        GatewayinfoExample e = new GatewayinfoExample();
                        GatewayinfoExample.Criteria c = e.createCriteria();
                        c.andPiMacEqualTo(mac);
                        List<Gatewayinfo> old_gateways = gatewayinfoMapper.selectByExample(e);
                        if (old_gateways != null && old_gateways.size() == 1) {
                            Gatewayinfo oldn = old_gateways.get(0);
                            oldn.setStatus(state);
                            oldn.setFrequency(frequency);
                            if (!ip.isEmpty()) oldn.setIp(ip);
                            if (!lat.isEmpty()) oldn.setLatitude(Double.parseDouble(lat));
                            if (!lng.isEmpty()) oldn.setLongitude(Double.parseDouble(lng));
                            gatewayinfoMapper.updateByExample(oldn, e);
                        }
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public void startNode(List<Integer> node_ids) { socket.emit("start node",node_ids);}

    public void stopNode(List<Integer> node_ids) { socket.emit("stop node",node_ids); System.out.println("stop");}

    public void restartNode(List<Integer> node_ids) { socket.emit("restart node",node_ids); }

    public void burnNode(List<Integer> node_ids, String filename,byte[] bin_file) {
        for(int id: node_ids){
            setSendData(id,true);
        }
        socket.emit("burn node",node_ids, filename,bin_file);
    }

    public void eraseNode(List<Integer> node_ids) { socket.emit("erase node",node_ids); }

    public void restartRpi(List<Integer> node_ids) { socket.emit("restart rpi",node_ids); }

    public void getLogFile(int node_id, Date starttime, Date endtime){ socket.emit("get log",node_id, starttime, endtime); }

    public void getDataFile(int node_id, Date starttime, Date endtime, boolean batchMode){
        socket.emit("get nodedata file", node_id, starttime, endtime, new Ack() {
            @Override
            public void call(Object... args) {

                String res = (String) args[0];

                System.out.println("get nodedata file " + node_id + res);

                if(res.equals("fail")){
                    String reason = (String) args[1];
                    Set<WebSocketSession> sessions = getMessageSession(node_id);
                    try{
                        for(WebSocketSession socketSession:sessions) {
                            socketSession.sendMessage(new TextMessage("fail-message-data "+node_id + " " +reason));
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (res.equals("succ")){
                    String file_name = (String) args[1];
                    byte[] file_data = (byte[]) args[2];

                    File file;
                    if(batchMode){
                        file = createFile(CONSTANT.FILE_PATH + File.separator + "zip" + File.separator + node_id, file_name);
                    }
                    else {
                        if(data_file_map.containsKey(node_id)){
                            file = data_file_map.get(node_id);
                            //System.out.println("contain");
                        }
                        else {
                            file = createFile(CONSTANT.FILE_PATH + File.separator + "data" + File.separator + node_id, file_name);
                        }
                        data_file_map.put(node_id, file);
                    }

                    try{
                        //true 追加，false覆盖
                        writeFile(file, file_data, !batchMode);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    //if(batchMode){
                    //    setShouldZipStart(getShouldZipStart() + 1);
                    //}
                    //单个的话就发送
                    if(!batchMode)
                    {
                        //System.out.println("send");
                        Set<WebSocketSession> sessions = getMessageSession(node_id);
                        System.out.println("session : " + sessions.isEmpty() + "size:" + sessions.size());
                        try{
                            for(WebSocketSession session:sessions){
                                // session.sendMessage(new TextMessage("hello world"));
                                session.sendMessage(new TextMessage("data file " + file.getPath().substring(CONSTANT.FILE_PATH.length())));
                                System.out.println("data file "+ file.getPath().substring(CONSTANT.FILE_PATH.length()));
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void getZipFile(String filename, int node_id){
        ZipUtil zipUtil = new ZipUtil(filename,CONSTANT.FILE_PATH + File.separator + "zip");
        try{
            zipUtil.zip();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Set<WebSocketSession> sessions = getMessageSession(node_id);
        System.out.println("准备发送");
        System.out.println("session : " + sessions.isEmpty() + "size:" + sessions.size());
        try{
            for(WebSocketSession session:sessions){
                session.sendMessage(new TextMessage("data file " + filename.substring(CONSTANT.FILE_PATH.length())));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //private void setSendLog(int node_id, boolean setting) { socket.emit("log send",node_id, setting); }

    public void setSendData(int node_id, boolean setting) {
        socket.emit("nodedata on", node_id, setting, new Ack() {
            @Override
            public void call(Object... objects) {
                if(objects[0].equals("succ")) return;
                Set<WebSocketSession> sessions = getDataSession(node_id);
                try{
                    for(WebSocketSession socketSession:sessions) {
                        socketSession.sendMessage(new TextMessage("get real time data failed"));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addNodeMessageSession(WebSocketSession session) {
        node_message_sessions.put(session,(String)session.getAttributes().get(CONSTANT.DEFAULT_WEBSOCKET_USERNAME));
        System.out.println("uri:" + session.getUri());
        System.out.println("username:" + node_message_sessions.getByKey(session));
    }

    public void removeNodeMessageSession(WebSocketSession session) {
        node_message_sessions.removeByKey(session);
    }

    /*public void addNodeLogSession(int node_id, WebSocketSession session) {
        if(!node_log_sessions.containsValue(node_id)){
            setSendLog(node_id, true);
        }
        node_log_sessions.put(session, node_id);
    }*/


    public void addNodeDataSession(int node_id, WebSocketSession session) {
        if(!node_data_sessions.containsValue(node_id)){
            setSendData(node_id, true);
        }
        node_data_sessions.put(session, node_id);
    }


    public void removeSession(WebSocketSession session) {
        if(node_message_sessions.containsKey(session))
            node_message_sessions.removeByKey(session);
        /*if(node_log_sessions.containsKey(session)){
            int id = node_log_sessions.getByKey(session);
            node_log_sessions.removeByKey(session);
            if(!node_log_sessions.containsValue(id)){
                setSendLog(id, false);
            }
        }*/
        if(node_data_sessions.containsKey(session)){
            int id = node_data_sessions.getByKey(session);
            node_data_sessions.removeByKey(session);
            if(!node_data_sessions.containsValue(id)){
                setSendData(id, false);
            }
        }
    }

    private Set<WebSocketSession> getMessageSession(int node_id) {
        //String username = nodeOrderService.getUser(node_id);
        Timeorder nowOrder = nodeOrderService.getNowOrder();
        if(nowOrder != null)
        {
            String username = nodeOrderService.getNowOrder().getUsername();
            if(username!=null)
                return node_message_sessions.getByValue(username);
        }
        return new HashSet<>();
    }

    /*private Set<WebSocketSession> getLogSession(int node_id) {
        return node_log_sessions.getByValue(node_id);
    }*/

    private Set<WebSocketSession> getDataSession(int node_id) {
        return node_data_sessions.getByValue(node_id);
    }

    private void writeFile(File file, byte[] content, boolean append){
        OutputStream output = null;
        BufferedOutputStream bufferedOutput = null;

        try{
            output = new FileOutputStream(file, append);

            bufferedOutput = new BufferedOutputStream(output);

            bufferedOutput.write(content);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            if(null!=bufferedOutput){
                try {
                    bufferedOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(null != output){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File createFile(String path, String name) {
        File file = new File(path);
        if(!file.exists()) file.mkdirs();
        file = new File(path + File.separator + name);
        /*while (file.exists()){
            System.out.println("file not exist");
            try{
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        while (file.exists()) {
            int split = name.lastIndexOf('.');
            name = name.substring(0,split)+"_new" + "." + name.substring(split + 1);
            file = new File(path + File.separator  + name);
        }
        return file;
    }

    public void test() {
        JSONObject a;
        List<Integer> node_ids = new ArrayList<>();
        node_ids.add(1);
        node_ids.add(2);
        socket.emit("start node",node_ids);
    }

    public void setShouldZipStart(int p){
        shouldZipStart = p;
    }

    public int getShouldZipStart(){
        return shouldZipStart;
    }

    public void startGateway(List<Integer> gateway_ids) { socket.emit("start gateway",gateway_ids);}

    public void stopGateway(List<Integer> gateway_ids) { socket.emit("stop gateway",gateway_ids);}

    public void restartGateway(List<Integer> gateway_ids) { socket.emit("restart gateway",gateway_ids); }

    //public void getGateway(List<Integer> gateway_ids) { socket.emit("get gateway",gateway_ids); }

    public void changeGateway(List<Integer> gateway_ids, Integer frequency){socket.emit("change frequency", gateway_ids, frequency);};

    /*private Emitter.Listener sendPong = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
               socket.emit("pong", "pong");
               System.out.println("rec pong");
        }
    };*/
}
