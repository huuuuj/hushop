package com.hujiao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@TableName("product_picture")
public class Picture implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("product_id")
    @JsonProperty("product_id")
    private Integer productId;
    @JsonProperty("product_picture")
    @TableField("product_picture")
    private String productPicture;
    private String intro;
}
