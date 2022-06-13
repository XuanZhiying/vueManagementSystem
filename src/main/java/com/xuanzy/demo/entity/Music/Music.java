package com.xuanzy.demo.entity.Music;

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
 * Create: 2022-05-28 11:18
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("music")
public class Music {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "musicName")
    private String musicName;

    @TableField(value = "musicType")
    private Integer musicType;

    @TableField(value = "author")
    private String author;

    @TableField(value = "musicImageFile")
    private String musicImageFile;//md5

    @TableField(value = "musicFile")
    private String musicFile;//音乐Url地址,md5

    @TableField(value = "lyric")
    private String lyric;//歌词

    @TableField(value = "enable")
    private Integer enable=1;


}
