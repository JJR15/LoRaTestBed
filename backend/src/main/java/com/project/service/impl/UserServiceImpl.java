package com.project.service.impl;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.project.mapper.NodeinfoMapper;
import com.project.mapper.UserMapper;
import com.project.model.*;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by DongBaishun on 2017/7/10.
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    private static final int SALT_LENGTH = 32;

    @Autowired
    private NodeinfoMapper nodeinfoMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> listUsers() {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameIsNotNull();
        return userMapper.selectByExample(example);
    }

    @Override
    public int loginCheck(String username, String password) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        User user = userMapper.selectByPrimaryKey(username);
        if(user == null) return 0;
        if (user.getStatus() == 0) {
            return 2; //未激活
        }
        String pwdHash = user.getPwdhash();
        String pwdSalt = user.getPwdsalt();
        String pwdHashCheck = Hashing.sha256().newHasher().putString(
                password + pwdSalt, Charsets.UTF_8).hash().toString();
        if (pwdHash.equals(pwdHashCheck)) {
            return 1;
        }
        else return -1;
    }
    @Override
    public User selectUserByName(String username){
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        return userMapper.selectByPrimaryKey(username);
    }

    @Override
    public List<User> selectUsers() {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameIsNotNull();
        return userMapper.selectByExample(example);
    }

    @Override
    public boolean addUser(String username, String password) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() != 0) { //用户已存在
            return false;
        }
        String salt = nextSalt();
        String pwd = Hashing.sha256().newHasher().putString(
                password + salt, Charsets.UTF_8).hash().toString();
        User record = new User(username, pwd, salt, 0); //未激活状态
        return  userMapper.insert(record) == 1;
    }

    @Override
    public boolean deleteUser(String username) {
        return userMapper.deleteByPrimaryKey(username) == 1;
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user) == 1;
    }

    @Override
    public List<String> updateUserBatch(List<User> users) {
        List<String> res = new ArrayList<>();
        if (users != null && users.size() != 0) {
            for (User item : users) {
                if (userMapper.updateByPrimaryKeySelective(item) != 1) {
                    res.add(item.getUsername());
                }
            }
        }
        return res;
    }

    public static String nextSalt() {
        Random ranGen = new SecureRandom();
        byte[] aesKey = new byte[SALT_LENGTH];
        ranGen.nextBytes(aesKey);
        StringBuilder hexString = new StringBuilder();
        for (byte key :
                aesKey) {
            String hex = Integer.toHexString(0xff & key);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
