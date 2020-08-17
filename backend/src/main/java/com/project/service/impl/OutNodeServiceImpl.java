package com.project.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.project.mapper.OutNodeMapper;
import com.project.model.*;
import com.project.service.OutNodeService;
import org.apache.ibatis.jdbc.Null;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.*;


@Service("outNodeServiceImpl")
public class OutNodeServiceImpl implements OutNodeService {

    @Autowired
    private OutNodeMapper outNodeMapper;

    private final static Base64.Encoder encoder = Base64.getEncoder();

    private static String host = "tcp://166.111.80.117:1883";

    int[] maxPacketLen = new int[]{51, 51, 51, 115, 222, 222};

    class SendClient extends Thread{
        public MqttClient client;
        public MqttConnectOptions options;
        public InputStream inputStream;
        public String devEui, filePath, topic;
        public boolean exit = true;
        public int packetLen,pos;
        public long fileLength;

        SendClient(String devEui,String filePath){
            this.devEui=devEui;
            this.filePath = filePath;
            topic = "application/14/device/" + devEui + "/tx";
            try{
                client = new MqttClient(host, "spring", new MemoryPersistence());
                options = new MqttConnectOptions();
                options.setAutomaticReconnect(true);
                options.setCleanSession(true);
                options.setConnectionTimeout(10);
                client.connect(options);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        public void startSend(int startPos, int packetLen){
            this.packetLen=packetLen;
            this.pos=startPos;
            try{
                File file = new File(filePath);
                fileLength = file.length();
                inputStream = new FileInputStream(file);
                inputStream.skip(startPos);
                this.run();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            super.run();
            try{
                byte[] tempBytes = new byte[packetLen-1];
                int readLen;
                while((readLen=inputStream.read(tempBytes,4,packetLen-5))>0 && !exit){
                    pos+=readLen;
                    tempBytes[0]=(byte)(pos&0xff);
                    tempBytes[1]=(byte)((pos>>8)&0xff);
                    tempBytes[2]=(byte)((pos>>16)&0xff);
                    tempBytes[3]=(byte)((pos>>24)&0xff);
                    if(pos>=fileLength){
                        tempBytes[3] |=0x80;
                    }
                    String encodedData = encoder.encodeToString(tempBytes);
                    JSONObject mqtt_data = new JSONObject();
                    mqtt_data.put("confirmed",true);
                    mqtt_data.put("fPort",3);
                    mqtt_data.put("data",encodedData);
                    client.publish(topic,mqtt_data.toJSONString().getBytes(),2,false);
                    sleep(100);
                }
                inputStream.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    HashMap<String, SendClient> sendMap = new HashMap<>();

    @Override
    public List<OutNode> getAllOutNodes() {
        OutNodeExample e = new OutNodeExample();
        OutNodeExample.Criteria c = e.createCriteria();
        c.andIdIsNotNull();
        return outNodeMapper.selectByExample(e);
    }

    private String getJwt(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://loraserver.thulpwan.top/api/internal/login";
        Map<String, String> map= new HashMap<String, String>();
        map.put("username","admin");
        map.put("password","adminlllllora!");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<HashMap> request = new HttpEntity<HashMap>((HashMap)map, headers);
        String jwt = restTemplate.postForObject(url,request,String.class);
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
    public void mqttSend(String devEui,String filePath,int startPos ,int sf) {
        SendClient sendClient;
        if(sendMap.containsKey(devEui)){
            sendClient = sendMap.get(devEui);
            sendClient.exit=true;
            if(filePath.equals("")){
                filePath=sendClient.filePath;
            }
            sendMap.remove(devEui,sendClient);
        }
        sendClient = new SendClient(devEui, filePath);
        sendMap.put(devEui, sendClient);
        if(sf<7||sf>12){
            sf=7;
        }
        sendClient.startSend(startPos,maxPacketLen[12-sf]);
    }
}
