package com.project.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.project.mapper.OutNodeMapper;
import com.project.model.*;
import com.project.service.OutNodeService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.nio.ByteBuffer;
import java.util.*;


@Service("outNodeServiceImpl")
public class OutNodeServiceImpl implements OutNodeService {

    @Autowired
    private OutNodeMapper outNodeMapper;

    private  MqttClient client;

    private static String host = "166.111.80.117:1883";

    public OutNodeServiceImpl(){
        try{
            client = new MqttClient(host, "spring", new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            client.connect(options);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OutNode> getAllOutNodes() {
        List<OutNode> L = outNodeMapper.selectAllOutNode();
        return L;
    }

    private String getJwt(){
        Map<String, String> map = new HashMap<>();
        map.put("username","admin");
        map.put("password","adminlllllora");
        RestTemplate restTemplate = new RestTemplate();
        String jwt = restTemplate.getForObject("https://loraserver.thulpwan.top/api/internal/login",String.class,map);
        return JSONObject.parseObject(jwt).getString("jwt");
    }

    @Override
    public void deleteDeviceQueue(String dev_eui){
        String jwt = getJwt();
        String req_url = "https://loraserver.thulpwan.top/api/devices/"+dev_eui+"/queue";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Grpc-Metadata-Authorization","Bearer "+jwt);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(req_url,HttpMethod.DELETE,new HttpEntity<String>(headers),String.class,new HashMap<>());

    }

    @Override
    public void mqttSend(MultipartFile bin_file, String topic, int start, int partLen) {
        partLen -= 5;
        int length = start;
        byte[] diff;
        try {
            diff = bin_file.getBytes();
        }
        catch (Exception e){
            return;
        }

        while(length<diff.length){
            length += partLen;
            int header;
            if(length >= diff.length){
                header= (length - partLen) | 0x80000000;
            }
            else{
                header = length - partLen;
            }
            ByteBuffer buf = ByteBuffer.allocate(partLen+4);
            buf.putInt(header);
            buf.put(diff,length-partLen,partLen);
            byte[] data = new byte[buf.remaining()];
            buf.get(data,0,data.length);

            JSONObject mqtt_data = new JSONObject();
            mqtt_data.put("confirmed",true);
            mqtt_data.put("fPort",3);
            mqtt_data.put("data",true);
            try{
                client.publish(topic,mqtt_data.toJSONString().getBytes(),2,false);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
