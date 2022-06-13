package com.xuanzy.demo.service.musicService.musicServiceImpl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Music.Music;
import com.xuanzy.demo.mapper.musicMapper.MusicMapper;
import com.xuanzy.demo.service.musicService.MusicService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Author: 轩志颍
 * Create: 2022-06-06 14:52
 **/
@Service
@DS("mysqlmusicdata")
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {

    @Resource
    MusicMapper musicMapper;

    @Override
    public Result saveMusic(MultipartFile file, Music music) {
        if (music.getId() == null) {
            return Result.success(musicMapper.insert(music));
        } else {
            return Result.success(musicMapper.updateById(music));
        }
    }

    @Override
    public Result pageMusic(Integer current, Integer size, Music music) {
        Page<Music> musicPage = new Page<>(current, size);

        return Result.success(musicMapper.selectMusicAndPage(musicPage,music));
    }

}
