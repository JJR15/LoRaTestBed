package com.project.controller;

import com.alibaba.fastjson.JSON;
import com.project.mapper.NodeinfoMapper;
import com.project.mapper.UserMapper;
import com.project.model.*;
import com.project.service.SystemInfoService;
import com.project.service.UserService;
import com.project.util.SendMailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DongBaishun on 2017/7/11.
 */

@Controller
//拦截http://localhost/lora/user
@RequestMapping("/user")
@SessionAttributes({"username", "currentUser"})
public class UserController {

    @Autowired
    private NodeinfoMapper nodeinfoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;


    @Autowired
    private SystemInfoService systemInfoService;

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String gotoRegister(HttpServletRequest request) {
        String language = request.getParameter("language");
        if ("en".equals(language))
            return "register_en";
        else return "register";
    }


    @RequestMapping(value = "register", method = RequestMethod.POST)
    public
    @ResponseBody
    String register(@RequestParam("username") String username,
                    @RequestParam("password") String password,
                    @RequestParam("email") String email,
                    @RequestParam(name = "city", required = false, defaultValue = "") String city,
                    @RequestParam(name = "institute", required = false, defaultValue = "") String institute,
                    @RequestParam(name = "reason", required = false, defaultValue = "") String reason) {
        Map<String, Boolean> res = new HashMap<>();
        boolean registerRes = false;
        Thread th = new Thread(new Runnable() {
            public void run() {
                try {
                    SendMailUtil.sendMessage("smtp.163.com", "thu_dandelion@163.com",
                            "THUtestbed2018", "thu_dandelion@163.com", "user:" + username + "的申请",
                            "申请内容(SSL发送方式):<br>" +
                                    "institute:" + institute + "<br/>" +
                                    "city:" + city + "<br/>" +
                                    "email:" + email + "<br/>" +
                                    "username:" + username + "<br/>" +
                                    "password:" + password + "<br/>" +
                                    "reason:" + reason + "<br/>"
                            ,
                            "text/html;charset=UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                    res.put("code", false);
                }
            }
        });
        th.start();
        registerRes = userService.addUser(username, password);
        res.put("code", registerRes);
        return JSON.toJSONString(res);
    }

    //restful 查询用户
    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public ResponseEntity<User> queryUserById(@PathVariable("username") String username) {
        try {
            User user = userService.selectUserByName(username);
            if (null == user) {
                // 资源不存在 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            // 资源存在 200
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 出现异常，服务器内部错误
        // 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    // 查询用户
    @RequestMapping(value = "getUsers", method = RequestMethod.POST)
    public
    @ResponseBody
    String queryUser(@ModelAttribute("currentUser") User currentUser) {
        Map<String, Object> res = new HashMap<>();
        if (currentUser.getStatus() == 3) { // 管理员权限
            try {
                List<User> users = userService.selectUsers();
                List<Object> itemList = new ArrayList<>();
                for (User item : users) {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("username", item.getUsername());
                    itemMap.put("status", item.getStatus());
                    itemList.add(itemMap);
                }
                res.put("users", itemList);
                res.put("count", itemList.size());
                res.put("code", 1);
            } catch (Exception e) {
                e.printStackTrace();
                res.put("code", 0);
            }
        }
        return JSON.toJSONString(res);
    }

    //restful 增加用户
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveUser(String username, String password) {
        try {
            userService.addUser(username, password);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 出现异常，服务器内部错误
        // 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                null);
    }

    // 激活用户
    @RequestMapping(value = "activateUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String activateUser(@ModelAttribute("currentUser") User currentUser,
                        @RequestParam("username") String username) {
        Boolean res = false;
        if (currentUser.getStatus() == 3) { // 管理员权限
            try {
                User user = new User();
                user.setUsername(username);
                user.setStatus(1);
                res = userService.updateUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return JSON.toJSONString(res);
    }

    // 修改用户权限
    @RequestMapping(value = "authority", method = RequestMethod.POST)
    public
    @ResponseBody
    String updateAuthority(@ModelAttribute("currentUser") User currentUser,
                           @RequestParam("username") String username,
                           @RequestParam("power") Integer power) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        String[] usernameArray = username.split(" ");
        List<String> usernames = new ArrayList<>();
        for (int i = 0, len = usernameArray.length; i < len; i++) {
            usernames.add(usernameArray[i]);
        }
        if (currentUser.getStatus() == 3 && power <= 3 && power >= 0) { // 管理员权限
            String msg = "";
            List<User> users = new ArrayList<>();
            if (usernames.size() != 0) {
                for (String item : usernames) {
                    User user = new User();
                    user.setUsername(item);
                    user.setStatus(power);
                    users.add(user);
                }
            }
            List<String> errorList = userService.updateUserBatch(users);
            if (errorList != null && errorList.size() != 0) {
                for (String item : errorList) {
                    msg = msg.concat("无法通过审核的用户:" + item + "\n");
                }
            }
            if (msg.isEmpty()) {
                res.put("code", 1);
                res.put("res", "success");
            } else {
                res.put("code", 0);
                res.put("res", msg);
            }
        }
        return JSON.toJSONString(res);
    }

    // 激活用户
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public
    @ResponseBody
    String updatePassword(@ModelAttribute("username") String username,
                          @RequestParam("newPassword") String newPassword) {
        boolean updateRes = false;
        try {
            User user = new User();
            user.setUsername(username);
            user.setPwdhash(newPassword);
            updateRes = userService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Boolean> res = new HashMap<>();
        res.put("code", updateRes);
        return JSON.toJSONString(res);
    }

    //restful 更新用户
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(String username, String password) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPwdhash(password);
            userService.updateUser(user);
            // 204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 出现异常，服务器内部错误
        // 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                null);
    }

    // 返回首页展示数据
    @RequestMapping(value = "index/data", method = RequestMethod.GET)
    public
    @ResponseBody
    String getIndexData() {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameIsNotNull();
        int userTotal = userMapper.countByExample(userExample);

        NodeinfoExample nodeinfoExample = new NodeinfoExample();
        NodeinfoExample.Criteria criteria1 = nodeinfoExample.createCriteria();
        criteria1.andIdIsNotNull();
        int nodeTotal = nodeinfoMapper.countByExample(nodeinfoExample);

        criteria1.andStateEqualTo(1);
        int onlineTotal = nodeinfoMapper.countByExample(nodeinfoExample);

        Map<String, Object> res = new HashMap<>();
        res.put("userTotal", userTotal);
        res.put("nodeTotal", nodeTotal);
        res.put("onlineTotal", onlineTotal);
        return JSON.toJSONString(res);
    }
}
