package com.hujiao.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hujiao.constants.UserConstants;
import com.hujiao.param.UserCheckParam;
import com.hujiao.param.UserLoginParam;
import com.hujiao.pojo.User;
import com.hujiao.user.mapper.UserMapper;
import com.hujiao.user.service.UserService;
import com.hujiao.utils.MD5Util;
import com.hujiao.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public R check(UserCheckParam userCheckParam) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",userCheckParam.getUserName());
        Long l = userMapper.selectCount(userQueryWrapper);
        if (l==0){
            return R.ok("账号可用");
        }
        return R.fail("账号已存在");
    }

    @Override
    public R register(User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",user.getUserName());
        Long l = userMapper.selectCount(userQueryWrapper);
        if (l!=0){
            return R.fail("账号已存在");
        }
        String encode = MD5Util.encode(user.getPassword()+ UserConstants.USER_SLAT);
        user.setPassword(encode);
        int insert = userMapper.insert(user);
        if (insert!=1){
            return R.fail("注册失败，请重试");
        }
        return R.ok("注册成功");
    }

    @Override
    public R login(UserLoginParam param) {
        String encode = MD5Util.encode(param.getPassword() + UserConstants.USER_SLAT);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",param.getUserName());
        userQueryWrapper.eq("password",encode);
        User user = userMapper.selectOne(userQueryWrapper);
        if (user==null){
           return R.fail("账号或密码错误");
        }
        //不用返回pwd
        user.setPassword(null);
        return R.ok("登录成功",user);
    }
}
