package com.project.service;

import com.project.model.OutNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface OutNodeService {

    List<OutNode> getAllOutNodes(); //获取所有节点信息

    void deleteDeviceQueue(String dev_eui);

    public void mqttSend(String devEui,String filePath,int startPos ,int sf);

}
