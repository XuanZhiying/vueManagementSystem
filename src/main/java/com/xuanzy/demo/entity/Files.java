package com.xuanzy.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Author: 轩志颍
 * Create: 2022-04-21 20:44
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "vd_file")
public class Files {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "uuid")
    private String uuid;

    @TableField(value = "OldFileName")
    private String oldFileName;

    @TableField(value = "newFileName")
    private String newFileName;

    @TableField(value = "type")
    private String type;

    @TableField(value = "size")
    private Integer size;

    @TableField(value = "url")
    private String url;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "uploadTime")
    private Date uploadTime;

    @TableField(value = "md5")
    private String md5;

    @TableField(value = "isDelete")
    private Boolean isDelete;

    @TableField(value = "enable")
    private Integer enable=1;
}
