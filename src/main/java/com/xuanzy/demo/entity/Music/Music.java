package com.xuanzy.demo.entity.Music;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Music对象",description = "music对象实体类")
@TableName("music")
public class Music {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "音乐名称")
    @TableField(value = "musicName")
    private String musicName;

    @ApiModelProperty(value = "音乐类型，integer类型参数")
    @TableField(value = "musicType")
    private Integer musicType;

    @ApiModelProperty(value = "音乐作者")
    @TableField(value = "author")
    private String author;

    @ApiModelProperty(value = "音乐图片uuid")
    @TableField(value = "musicImageFileUuid")
    private String musicImageFileUuid;//md5

    @ApiModelProperty(value = "音乐图片文件名")
    @TableField(value = "musicImageFileName")
    private String musicImageFileName;

    @ApiModelProperty(value = "音乐图片url链接")
    @TableField(exist = false)
    private String musicImageFileUrl;

    @ApiModelProperty(value = "音乐文件url链接")
    @TableField(exist = false)
    private String musicFileUrl;

    @ApiModelProperty(value = "音乐类型名称")
    @TableField(exist = false)
    private String musicTypeName;

    @ApiModelProperty(value = "音乐文件uuid")
    @TableField(value = "musicFileUuid")
    private String musicFileUuid;//音乐Url地址,md5

    @ApiModelProperty(value = "音乐文件名称")
    @TableField(value = "musicFileName")
    private String musicFileName;

    @ApiModelProperty(value = "歌词")
    @TableField(value = "lyric")
    private String lyric;//歌词

    @ApiModelProperty(value = "逻辑删除字段(1:未删除，0:已删除)")
    @TableField(value = "enable")
    private Integer enable=1;


}
