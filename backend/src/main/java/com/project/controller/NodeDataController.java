package com.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes({"username", "currentUser"})
@RequestMapping("/nodeData")
public class NodeDataController {


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public
    @ResponseBody
    String nodeUpdate(@RequestParam("id") int id,
                      @RequestParam("file") MultipartFile bin_file,
                      @ModelAttribute("username") String username){
        if(username==null) return "";
        System.out.println(id + " " + bin_file.getOriginalFilename());
        return "success";
    }

    public void handleUpdate(){

    }

    @RequestMapping(value = "/data", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    public void nodeData(HttpServletRequest request, HttpServletResponse response){
        JSONObject object = JSONObject.parseObject(request.getQueryString());
        if(object.getInteger("fPort")==3) {
            handleUpdate();
        }
    }

}

