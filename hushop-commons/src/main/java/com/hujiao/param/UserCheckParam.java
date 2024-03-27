package com.hujiao.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 接受前端参数的param
 * TODO 要使用jsr 303的注解进行参数校验
 */
@Data
public class UserCheckParam {
    @NotBlank//校验不为空
    private String userName;

}
