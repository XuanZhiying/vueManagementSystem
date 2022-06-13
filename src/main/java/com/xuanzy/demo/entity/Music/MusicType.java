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
 * Create: 2022-06-06 14:22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "musictype")
public class MusicType {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "musicType")
    private String musicType;


}
