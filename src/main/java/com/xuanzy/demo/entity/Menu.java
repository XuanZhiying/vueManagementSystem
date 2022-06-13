package com.xuanzy.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-29 19:30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName(value = "vd_menu")
public class Menu {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "pid")
    private Integer pid;

    @TableField(value = "name")
    private String name;

    @TableField(value = "path")
    private String path;

    @TableField(value = "icon")
    private String icon;

    @TableField(value = "description")
    private String description;

    @TableField(value = "page_path")
    private String pagePath;

    @TableField(exist = false)
    private List<Menu> children;
}
