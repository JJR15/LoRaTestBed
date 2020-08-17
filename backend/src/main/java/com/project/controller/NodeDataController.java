package com.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.project.mapper.OutNodeMapper;
import com.project.model.OutNode;
import com.project.model.OutNodeExample;
import com.project.service.OutNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Base64;

@Controller
@SessionAttributes({"username", "currentUser"})
@RequestMapping("/nodeData")
public class NodeDataController {
    @Autowired
    private OutNodeMapper outNodeMapper;

    @Autowired
    private OutNodeService outNodeService;

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public
    @ResponseBody
    String nodeUpdate(HttpServletRequest request,
                      @RequestParam("id") int id,
                      @RequestParam("file") MultipartFile delta,
                      @ModelAttribute("username") String username){
        if(username==null) return "";

        OutNode node = outNodeMapper.selectByPrimaryKey(id);
        String filepath=request.getServletContext().getRealPath("/")+"updates\\"+node.getDevEui()+"\\";

        System.out.println(id + " " + delta.getOriginalFilename());
        System.out.println(filepath);

        File file=new File(filepath);
        if(!file.exists()){//目录不存在就创建
            file.mkdirs();
        }
        try{
            delta.transferTo(new File(file+"\\"+"delta.bin"));//保存文件
        }
        catch (Exception e){
            return "error when saving the delta";
        }

        String topic = "application/14/device/" + node.getDevEui() + "/tx";
        outNodeService.deleteDeviceQueue(node.getDevEui());
        outNodeService.mqttSend(node.getDevEui(),filepath+"delta.bin",0,node.getSpreadingFactor());

        return "success";
    }

    @RequestMapping(value = "/data", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    public void nodeData(HttpServletRequest request, HttpServletResponse response){
        JSONObject data = JSONObject.parseObject(request.getQueryString());
        if(data.getInteger("fPort")==3) {
            handleUpdate(data);
        }
    }

    public void handleUpdate(JSONObject data){
        Base64.Decoder decoder = Base64.getDecoder();
        String devEui = data.getString("devEUI");
        String payload_encoded = data.getString("data");
        byte[] payload = decoder.decode(payload_encoded);
        int start = payload[0]+(payload[1]<<8)+(payload[2]<<16)+(payload[3]<<24);
        int sf = 12-data.getJSONObject("txInfo").getInteger("dr");
        if(start==0xffffffff){
            return;
        }
        outNodeService.deleteDeviceQueue(devEui);
        outNodeService.mqttSend(devEui,"",start,sf);
    }

}

