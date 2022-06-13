package com.xuanzy.demo.mapper.musicMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuanzy.demo.entity.Music.MusicFile;
import org.apache.ibatis.annotations.Param;

/**
 * Author: 轩志颍
 * Create: 2022-06-07 18:59
 **/
public interface MusicFileMapper extends BaseMapper<MusicFile> {
    MusicFile selectOneByMd5(String md5);
}
