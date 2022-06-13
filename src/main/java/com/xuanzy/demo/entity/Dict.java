package com.xuanzy.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Author: 轩志颍
 * Create: 2022-04-30 22:18
 **/
@Data
@TableName("vd_dict")
public class Dict {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String name;

    private String value;

    private String type;
}
