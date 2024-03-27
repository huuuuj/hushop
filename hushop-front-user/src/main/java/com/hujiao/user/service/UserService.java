package com.hujiao.user.service;

import com.hujiao.param.UserCheckParam;
import com.hujiao.param.UserLoginParam;
import com.hujiao.pojo.User;
import com.hujiao.utils.R;

public interface UserService {

    /**
     * 检查账号是否可用
     * @param userCheckParam
     * @return
     */
    R check(UserCheckParam userCheckParam);

    R register(User user);

    R login(UserLoginParam param);
}
