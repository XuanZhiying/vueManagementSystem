package com.xuanzy.demo.service.musicService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Music.MusicType;

/**
 * Author: 轩志颍
 * Create: 2022-06-22 19:06
 **/
public interface MusicTypeService extends IService<MusicType> {
    Result saveMusicType(MusicType musicType);

    Result pageMusicType(Integer pageNum,Integer pageSize,MusicType musicType);
}
