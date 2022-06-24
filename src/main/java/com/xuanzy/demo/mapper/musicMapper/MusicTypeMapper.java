package com.xuanzy.demo.mapper.musicMapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuanzy.demo.entity.Music.MusicType;
import org.apache.ibatis.annotations.Param;

/**
 * Author: 轩志颍
 * Create: 2022-06-22 19:05
 **/
@DS("mysqlmusicdata")
public interface MusicTypeMapper extends BaseMapper<MusicType> {
    IPage<MusicType> selectMusicTypeAndPage(Page<MusicType> page,@Param("musicType") MusicType musicType);
}
