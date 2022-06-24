package com.xuanzy.demo.service.musicService.musicServiceImpl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Music.MusicType;
import com.xuanzy.demo.mapper.musicMapper.MusicTypeMapper;
import com.xuanzy.demo.service.musicService.MusicTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Author: 轩志颍
 * Create: 2022-06-22 19:06
 **/
@Service
@DS("mysqlmusicdata")
public class MusicTypeServiceImpl extends ServiceImpl<MusicTypeMapper, MusicType> implements MusicTypeService {
    @Resource
    MusicTypeMapper musicTypeMapper;

    @Override
    public Result saveMusicType(MusicType musicType) {
        if (musicType.getId() == null) {
            musicType.setCreateTime(new Date());
            musicTypeMapper.insert(musicType);
            return Result.success("音乐分类信息保存成功");
        } else {
            musicType.setCreateTime(new Date());
            musicTypeMapper.updateById(musicType);
            return Result.success("音乐分类信息更新成功");
        }
    }

    @Override
    public Result pageMusicType(Integer pageNum, Integer pageSize, MusicType musicType) {
        Page<MusicType> musicTypePage = new Page<>(pageNum, pageSize);
        return Result.success(musicTypeMapper.selectMusicTypeAndPage(musicTypePage, musicType));
    }
}
