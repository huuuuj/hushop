package com.hujiao.user.controller;

import com.hujiao.param.UserCheckParam;
import com.hujiao.param.UserLoginParam;
import com.hujiao.pojo.User;
import com.hujiao.user.service.UserService;
import com.hujiao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 账号检查
     * @param userCheckParam
     * @param result
     * @return
     */
    @PostMapping("/check")
    public R check(@RequestBody @Validated/*使userName校验生效*/ UserCheckParam userCheckParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("账号为空，不可使用！");
        }
        return userService.check(userCheckParam);
    }

    /**
     * 账号注册
     * @param user
     * @param result
     * @return
     */
    @PostMapping("/register")
    public R register(@RequestBody @Validated User user,BindingResult result){
        if (result.hasErrors()){
            return R.fail("用户名长度不能超过8位，且相关参数不能为空,手机号不符合要求");
        }
        return userService.register(user);
    }

    @PostMapping("/login")
    public R login(@RequestBody @Validated UserLoginParam param, BindingResult result){
        if (result.hasErrors()){
            return R.fail("账号或密码不能为空");
        }
        return userService.login(param);
    }
}
