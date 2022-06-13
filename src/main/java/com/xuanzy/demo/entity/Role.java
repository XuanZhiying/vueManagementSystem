package com.xuanzy.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Author: 轩志颍
 * Create: 2022-04-29 11:15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "vd_role")
public class Role {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "description")
    private String description;

    @TableField(value = "flag")
    private String flag;

    @TableField(value = "enable")
    private Integer enable;
}
