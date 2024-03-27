package com.hujiao.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hujiao.pojo.Address;
import com.hujiao.user.mapper.AddressMapper;
import com.hujiao.user.service.AddressService;
import com.hujiao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressMapper addressMapper;
    @Override
    public R list(Integer userId) {
        QueryWrapper<Address> addressQueryWrapper = new QueryWrapper<>();
        addressQueryWrapper.eq("user_id",userId);
        List<Address> addresses = addressMapper.selectList(addressQueryWrapper);

        return R.ok("查询成功",addresses);
    }

    @Override
    public R save(Address address) {
        int insert = addressMapper.insert(address);
        if (insert==0){
            return R.fail("添加失败");
        }
        return R.ok("添加成功");
    }

    @Override
    public R remove(Integer id) {
        int i = addressMapper.deleteById(id);
        if (i==0) {
            R.fail("删除失败");
        }
        return R.ok("删除成功");
    }
}
