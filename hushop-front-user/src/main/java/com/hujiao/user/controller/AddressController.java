package com.hujiao.user.controller;

import com.hujiao.param.AddressListParam;
import com.hujiao.param.AddressRemoveParam;
import com.hujiao.pojo.Address;
import com.hujiao.user.service.AddressService;
import com.hujiao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @PostMapping("/list")
    public R list(@RequestBody @Validated AddressListParam param, BindingResult result){
        if (result.hasErrors()){
            return R.fail("用户id呢？");
        }
        return addressService.list(param.getUserId());
    }

    @PostMapping("/save")
    public R save(@RequestBody @Validated Address address, BindingResult result){
        if (result.hasErrors()){
            return R.fail("保存失败，参数格式不对");
        }
        return addressService.save(address);
    }

    @PostMapping("/remove")
    public R remove(@RequestBody @Validated AddressRemoveParam param, BindingResult result){
        if (result.hasErrors()){
            return R.fail("删除失败，参数不能为空");
        }
        return addressService.remove(param.getId());
    }

}
