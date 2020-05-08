package com.project.service;

import org.springframework.web.socket.WebSocketSession;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface JuraService {

    void connectJuraServer() throws Exception;

    void addNodeMessageSession(WebSocketSession session);

    void removeNodeMessageSession(WebSocketSession session);

    //void addNodeLogSession(int node_id, WebSocketSession session);

    void addNodeDataSession(int node_id, WebSocketSession session);

    void removeSession(WebSocketSession session);

    void startNode(List<Integer> node_ids);

    void stopNode(List<Integer> node_ids);

    void restartNode(List<Integer> node_ids);

    void burnNode(List<Integer> node_ids, String filename,byte[] bin_file);

    void eraseNode(List<Integer> node_ids);

    void restartRpi(List<Integer> node_ids);

    void getLogFile(int node_id, Date starttime, Date endtime);

    void getDataFile(int node_id, Date starttime, Date endtime, boolean batchMode);

    void getZipFile(String filename, int node_id);

    void startGateway(List<Integer> gateway_ids);

    void stopGateway(List<Integer> gateway_ids);

    void restartGateway(List<Integer> gateway_ids);

    //void getGateway(List<Integer> gateway_ids);

    void changeGateway(List<Integer> gateway_ids,Integer frequency);

    void setShouldZipStart(int p);

    int getShouldZipStart();

    void showinfo();

    void setSendData(int node_id, boolean setting);
}
