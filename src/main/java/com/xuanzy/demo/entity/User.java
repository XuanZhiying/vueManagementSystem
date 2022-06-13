package com.xuanzy.demo.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-18 08:06
 **/

@ColumnWidth(30)
@ContentRowHeight(15)
@HeadRowHeight(25)
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "vd_user")
public class User {

    @ExcelIgnore
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @ExcelProperty("用户名")
    @TableField(value = "username")
    private String username;

//    @JsonIgnore
    @ExcelProperty("密码")
    @TableField(value = "password")
    private String password;

    @ExcelProperty("用户昵称")
    @TableField(value = "nickname")
    private String nickname;

    @ExcelProperty("邮箱")
    @TableField(value = "email")
    private String email;

    @ExcelProperty("电话")
    @TableField(value = "phone")
    private String phone;

    @ExcelProperty("地址")
    @TableField(value = "address")
    private String address;

    @ExcelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "createTime")
    private Date createTime;

    @ExcelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "updateTime")
    private Date updateTime;

    @ExcelProperty("用户头像链接")
    @TableField(value = "avatarUrl")
    private String avatarUrl;

    @TableField(exist = false)
    @ExcelIgnore
    private String token;

    @ExcelIgnore
    @JsonIgnore
    @TableField(value = "enable")
    private Integer enable=1;

    @ExcelIgnore
    @TableField(value = "role")
    private String role;

    @ExcelIgnore
    @TableField(exist = false)
    private List<Menu> menu;

    @ExcelIgnore
    @TableField(exist = false)
    private Integer pageNum;

    @ExcelIgnore
    @TableField(exist = false)
    private Integer pageSize;

}
