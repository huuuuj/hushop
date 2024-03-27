package com.hujiao.carousel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hujiao.carousel.mapper.CarouselMapper;
import com.hujiao.carousel.service.CarouselService;
import com.hujiao.pojo.Carousel;
import com.hujiao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public R list() {
        QueryWrapper<Carousel> carouselQueryWrapper = new QueryWrapper<>();
        carouselQueryWrapper.orderByDesc("priority");
        List<Carousel> carousels = carouselMapper.selectList(carouselQueryWrapper);
        List<Carousel> collect = carousels.stream().limit(4).collect(Collectors.toList());
        return R.ok(collect);
    }
}
