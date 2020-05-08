package com.project.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.service.UserService;

import com.project.service.impl.JuraServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class testController {
    @Autowired
    private UserService userService;
    @Autowired
    private JuraServiceImpl juraService;


    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String test(HttpServletRequest request){

        /*try{
            juraServiceImpl.connectJuraServer();
        }
        catch (Exception e){
            e.printStackTrace();
        }*/
        juraService.test();

        List<Integer> l = new ArrayList<>();
        l.add(1);
        l.add(2);
        l.add(1);

        JsonObject res = new JsonObject();
        JsonArray arr = new JsonArray();


        JsonObject a = new JsonObject();
        a.addProperty("id","1");
        arr.add(a);
        try{
            res.add("ids",arr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res.toString();
    }
}
