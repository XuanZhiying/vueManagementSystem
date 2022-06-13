package com.xuanzy.demo.mapper.musicMapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuanzy.demo.entity.Music.Music;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-06-06 14:42
 **/
@DS("mysqlmusicdata")
public interface MusicMapper extends BaseMapper<Music> {
    List<Music> selectAll();

    IPage<Music> selectMusicAndPage(Page<Music> page, @Param("music")Music music);
}
