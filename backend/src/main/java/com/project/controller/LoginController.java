package com.project.controller;

import com.alibaba.fastjson.JSON;
import com.project.model.User;
import com.project.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@Controller
//拦截http://localhost/lora/login
@RequestMapping("/login")
@SessionAttributes({"username"})
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String Login(HttpServletRequest request) {
        String language = request.getParameter("language");
        if("en".equals(language))
            return "login_en";
        //默認都是英文版
        else return "login_en";
    }

    //@CrossOrigin(origins = "http://localhost:8000", maxAge = 3600)
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String checkLogin(HttpServletRequest request,
                             HttpServletResponse response,
                             @RequestParam("username") String username,
                             @RequestParam("password") String password,
                             ModelMap modelMap) throws Exception{
        Integer checkRes = userService.loginCheck(username, password);
        Map<String, Object> res = new HashMap<>();
        if(checkRes == 1) {
            User currentUser = userService.selectUserByName(username);
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("currentUser", currentUser);
            modelMap.addAttribute("username", username);
            modelMap.addAttribute("currentUser", currentUser);
            res.put("code", 1);
            res.put("user_type", currentUser.getStatus());
            res.put("res", "success");
        }
        else if(checkRes == 0 || checkRes == 2) {
            res.put("code", 0);
            res.put("res", "该用户不存在");
        }
        else {
            res.put("code", -1);
            res.put("res", "用户名与密码不匹配");
        }
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        //System.out.println("ws out::" + "ws://" + request.getServerName() + ":" + 80 + request.getContextPath() + "/");
        return JSON.toJSONString(res);
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public
    @ResponseBody
    String logout(HttpServletRequest request, SessionStatus sessionStatus) {
        Map<String, Boolean> res = new HashMap<>();
        try {
            // 清除session
            sessionStatus.setComplete();
            Enumeration<String> em = request.getSession().getAttributeNames();
            while (em.hasMoreElements()) {
                String attrName = em.nextElement();
                request.getSession().setAttribute(attrName, null);
                request.getSession().removeAttribute(attrName);
            }
            request.getSession().invalidate();
        } catch (Exception e) {
            res.put("code", false);
            return JSON.toJSONString(res);
        }
        res.put("code", true);
        return JSON.toJSONString(res);
    }

}
