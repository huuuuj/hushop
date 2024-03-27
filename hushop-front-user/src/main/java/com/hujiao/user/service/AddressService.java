package com.hujiao.user.service;

import com.hujiao.pojo.Address;
import com.hujiao.utils.R;

public interface AddressService {

    R list(Integer userId);

    R save(Address address);

    R remove(Integer id);
}
