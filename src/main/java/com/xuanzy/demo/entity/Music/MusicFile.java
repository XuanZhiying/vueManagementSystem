package com.xuanzy.demo.entity.Music;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Author: 轩志颍
 * Create: 2022-06-06 14:24
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("musicfile")
public class MusicFile {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "fileName")
    private String fileName;

    @TableField(value = "fileUuid")
    private String fileUuid;

    @TableField(value = "fileMd5")
    private String fileMd5;

    @TableField(value = "uploadTime")
    private Date uploadTime;

    @TableField(exist = false)
    private String Url;

    @TableField(value = "uploadName")
    private String uploadName;

    @TableField(value = "fileType")
    private String fileType;

    @TableField(value = "enable")
    private Integer enable=1;
}
