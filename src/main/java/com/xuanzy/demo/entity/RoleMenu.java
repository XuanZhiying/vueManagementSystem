package com.xuanzy.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Author: 轩志颍
 * Create: 2022-04-30 23:50
 **/
@Data
@TableName("vd_role_menu")
public class RoleMenu {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer roleId;

    private Integer menuId;
}
